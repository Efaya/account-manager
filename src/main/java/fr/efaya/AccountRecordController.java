package fr.efaya;

import fr.efaya.domain.AccountRecord;
import fr.efaya.domain.AccountRecordService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by KTIFA FAMILY on 28/03/2016.
 */
@RestController
@RequestMapping("/accountRecord")
public class AccountRecordController {

    @Autowired
    private AccountRecordService accountRecordService;

    @RequestMapping(method = RequestMethod.GET)
    public List<AccountRecord> retrieveAccountRecords() {
        return accountRecordService.findAll();
    }

    @RequestMapping(value = "/monthly", method = RequestMethod.GET)
    public List<AccountRecord> retrieveAccountRecordsForDate(Date refDate) {
        LocalDate date = refDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate start = date.withDayOfMonth(1);
        LocalDate end = date.withDayOfMonth(date.lengthOfMonth());
        return accountRecordService.findByDateBetween(Date.from(Instant.from(start.atStartOfDay())), Date.from(Instant.from(end.atStartOfDay())));
    }

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException
    {
        File convFile = new File("tmp.csv");
        FileCopyUtils.copy(multipart.getBytes(), convFile);
        return convFile;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void importCsvFile(@RequestBody MultipartFile file) throws IOException, ParseException {
        if (FilenameUtils.isExtension(file.getOriginalFilename(), "csv")) {
            accountRecordService.importFile(multipartToFile(file));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public AccountRecord updateAccountRecord(@PathVariable String id, @RequestBody AccountRecord record) {
        return accountRecordService.save(record);
    }

    @RequestMapping(value = "/{id}/category", method = RequestMethod.POST)
    public void addLabelToCategory(@PathVariable String id, @RequestBody String label) {
        accountRecordService.addLabelToCategory(id, label);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeAccountRecord(@PathVariable String id) {
        accountRecordService.delete(id);
    }
}
