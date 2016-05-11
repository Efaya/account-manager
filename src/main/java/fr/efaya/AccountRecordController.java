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
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @RequestMapping(value = "/monthly", method = RequestMethod.POST)
    public MonthResponse retrieveAccountRecordsForDate(@RequestBody MonthRequest monthRequest) {
        LocalDate date = monthRequest.getMonthRef().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate start = date.minusMonths(monthRequest.getPastMonths()).withDayOfMonth(1);
        LocalDate end = date.minusMonths(monthRequest.getPastMonths()).withDayOfMonth(date.minusMonths(monthRequest.getPastMonths()).lengthOfMonth());
        List<AccountRecord> records = accountRecordService.findByDateBetween(start, end);
        List<AccountRecord> outcomes = records.stream().filter(r -> r.getValue() < 0.0).collect(Collectors.toList());
        List<AccountRecord> incomes = records.stream().filter(r -> r.getValue() > 0.0).collect(Collectors.toList());
        MonthResponse response = new MonthResponse();
        response.setRecords(records);
        response.setMonth(date.minusMonths(monthRequest.getPastMonths()).getMonth().getValue());
        response.setSumIncomes(incomes.stream().mapToDouble(r -> Math.floor(Math.abs(r.getValue()) * 100) / 100).sum());
        response.setSumOutcomes(outcomes.stream().mapToDouble(r -> Math.floor(Math.abs(r.getValue()) * 100) / 100).sum());
        response.setCategorizedIncomes(incomes.stream().collect(Collectors.groupingBy(AccountRecord::getCategory)));
        response.setCategorizedOutcomes(outcomes.stream().collect(Collectors.groupingBy(AccountRecord::getCategory)));
        return response;
    }

    @RequestMapping(value = "/yearly/{year}", method = RequestMethod.POST)
    public Double[] retrieveAccountRecordsForDate(@PathVariable Integer year, @RequestBody(required = false) String category) {
        final String cat = category != null ? category : "";
        Double[] values = new Double[12];
        for (Month month : Month.values()) {
            LocalDate start = LocalDate.of(year, month, 1);
            LocalDate end = LocalDate.of(year, month, month.maxLength());
            double sum = Math.floor(accountRecordService.findByDateBetween(start, end).stream().filter(r -> r.getCategory().equals(cat)).mapToDouble(r -> Math.abs(r.getValue())).sum() * 100) / 100;
            values[month.getValue() - 1] = sum;
        }
        return values;
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
