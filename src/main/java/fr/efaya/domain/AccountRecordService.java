package fr.efaya.domain;

import fr.efaya.database.AccountRecordsRepository;
import fr.efaya.database.CategoriesRepository;
import fr.efaya.domain.filereader.FileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by KTIFA FAMILY on 15/04/2016.
 */
@Service
public class AccountRecordService {

    @Autowired
    private AccountRecordsRepository accountRecordsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private List<FileParser> parsers;

    public void delete(String id) {
        accountRecordsRepository.delete(id);
    }

    public AccountRecord save(AccountRecord record) {
        return accountRecordsRepository.save(record);
    }

    public List<AccountRecord> findByDateBetween(LocalDate from, LocalDate to, String username) {
        return accountRecordsRepository.findByDateBetweenAndUsername(from, to, username);
    }


    public List<AccountRecord> findByDateBetweenAndCategory(LocalDate start, LocalDate end, String cat, String username) {
        return accountRecordsRepository.findByDateBetweenAndCategoryAndUsername(start, end, cat, username);
    }

    public List<AccountRecord> findAll() {
        return accountRecordsRepository.findAll();
    }

    public List<AccountRecord> findAllByUsername(String username) {
        return accountRecordsRepository.findByUsername(username);
    }

    public void importFile(File file, String username, String fileType) throws ParseException, IOException {
        if (parsers != null) {
            Optional<FileParser> parser = parsers.stream().filter(p -> p.accept(fileType)).findFirst();
            parser.ifPresent(p -> {
                try {
                    p.parseFile(file, username).stream().forEach(accountRecordsRepository::save);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        file.deleteOnExit();
    }

    public void addLabelToCategory(String id, String label) {
        AccountRecord record = accountRecordsRepository.findOne(id);
        Category category = categoriesRepository.findOne(record.getCategory());
        category.getValues().add(label);
        category.setValues(category.getValues());
        categoriesRepository.save(category);
    }
}
