package fr.efaya.domain;

import fr.efaya.database.AccountRecordsRepository;
import fr.efaya.database.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by KTIFA FAMILY on 15/04/2016.
 */
@Service
public class AccountRecordService {

    @Autowired
    private AccountRecordsRepository accountRecordsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    public void delete(String id) {
        accountRecordsRepository.delete(id);
    }

    public AccountRecord save(AccountRecord record) {
        return accountRecordsRepository.save(record);
    }

    public List<AccountRecord> findByDateBetween(Date from, Date to) {
        return accountRecordsRepository.findByDateBetween(from, to);
    }

    public List<AccountRecord> findAll() {
        return accountRecordsRepository.findAll();
    }

    private void handleRecord(String lineRecord, List<Category> categories) {
        String[] tokens = lineRecord.split(";");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = df.parse(tokens[0]);
            Float value = Float.parseFloat(tokens[1].replace(",", "."));
            String type = tokens[2];
            String label = tokens.length > 4 ? (value > 0 || tokens[2].length() == 0 ? tokens[5].trim() : tokens[4].trim()) : "";

            AccountRecord accountRecord = new AccountRecord();
            accountRecord.setDate(date);
            accountRecord.setLabel(label);
            accountRecord.setType(type);
            accountRecord.setValue(value);
            accountRecord.setCategory(guessCategory(label, categories));
            //System.out.println(date + " --> " + value + " (" + type + ") : " + label);

            accountRecordsRepository.save(accountRecord);
        } catch (Exception e) {
            System.out.println("ERROR : " + lineRecord);
        }
    }

    private String guessCategory(String label, List<Category> categories) {
        final String value = label.contains("   ") ? label.split("   ")[0] : label;
        for (Category c : categories) {
            if (c.getValues().indexOf(value) > -1 || c.getValues().stream().anyMatch(value::startsWith)) {
                return c.getId();
            }
        }
        return "";
    }

    public void importFile(File file) throws ParseException, IOException {
        String line;
        try (
                InputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                BufferedReader br = new BufferedReader(isr)
        ) {
            List<Category> categories = categoriesRepository.findAll();
            while ((line = br.readLine()) != null) {
                handleRecord(line, categories);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addLabelToCategory(String id, String label) {
        AccountRecord record = accountRecordsRepository.findOne(id);
        Category category = categoriesRepository.findOne(record.getCategory());
        category.getValues().add(label);
        category.setValues(category.getValues());
        categoriesRepository.save(category);
    }
}
