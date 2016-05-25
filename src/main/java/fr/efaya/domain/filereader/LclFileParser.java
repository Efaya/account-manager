package fr.efaya.domain.filereader;

import fr.efaya.database.CategoriesRepository;
import fr.efaya.domain.AccountRecord;
import fr.efaya.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by KTIFA FAMILY on 25/05/2016.
 */
@Component
public class LclFileParser implements FileParser {
    @Autowired
    private CategoriesRepository categoriesRepository;

    private static final String TYPE = "LCL";

    @Override
    public boolean accept(String fileType) {
        return TYPE.equals(fileType);
    }

    @Override
    public List<AccountRecord> parseFile(File file, String username) throws IOException {
        List<Category> categories = categoriesRepository.findAll();
        List<String> lines;
        List<AccountRecord> records = new ArrayList<>();
        try {
            lines = Files.readAllLines(file.toPath(), Charset.forName("ISO-8859-1"));
        } catch (MalformedInputException e) {
            try {
                lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
            } catch (MalformedInputException m) {
                throw new UnsupportedCharsetException("Unrecognized encoding");
            }
        }
        lines.stream().forEach(l -> {
            Optional<AccountRecord> record = handleRecord(l, categories, username);
            record.ifPresent(records::add);
        });
        return records;
    }

    private Optional<AccountRecord> handleRecord(String lineRecord, List<Category> categories, String username) {
        String[] tokens = lineRecord.split(";");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        AccountRecord accountRecord = null;
        try {
            Date date = df.parse(tokens[0]);
            Float value = Float.parseFloat(tokens[1].replace(",", "."));
            String type = tokens[2];
            String label = tokens.length > 4 ? (value > 0 || tokens[2].length() == 0 ? tokens[5].trim() : tokens[4].trim()) : "";

            accountRecord = new AccountRecord();
            accountRecord.setDate(date);
            accountRecord.setLabel(label);
            accountRecord.setType(type);
            accountRecord.setValue(value);
            accountRecord.setUsername(username);
            accountRecord.setCategory(guessCategory(label, categories));

        } catch (Exception e) {
            System.out.println("ERROR : " + lineRecord);
        }
        return Optional.ofNullable(accountRecord);
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
}
