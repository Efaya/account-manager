package fr.efaya;

import fr.efaya.domain.AccountRecord;
import fr.efaya.domain.AccountRecordService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by KTIFA FAMILY on 28/03/2016.
 */
@RestController
@RequestMapping("/accountRecord")
public class AccountRecordController {

    @Autowired
    private AccountRecordService accountRecordService;

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<AccountRecord> retrieveAllAccountRecords() {
        return accountRecordService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<AccountRecord> retrieveAccountRecords(Principal principal) {
        return accountRecordService.findAllByUsername(principal.getName());
    }

    @RequestMapping(value = "/monthly", method = RequestMethod.POST)
    public MonthResponse retrieveAccountRecordsForDate(@RequestBody MonthRequest monthRequest, Principal principal) {
        LocalDate date = monthRequest.getMonthRef().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate start = date.minusMonths(monthRequest.getPastMonths()).withDayOfMonth(1);
        LocalDate end = date.minusMonths(monthRequest.getPastMonths()).withDayOfMonth(date.minusMonths(monthRequest.getPastMonths()).lengthOfMonth());
        List<AccountRecord> records = accountRecordService.findByDateBetween(start, end, principal.getName());
        List<AccountRecord> outcomes = records.stream().filter(r -> r.getValue() < 0.0).collect(Collectors.toList());
        List<AccountRecord> incomes = records.stream().filter(r -> r.getValue() > 0.0).collect(Collectors.toList());
        MonthResponse response = new MonthResponse();
        response.setRecords(records);
        response.setMonth(date.minusMonths(monthRequest.getPastMonths()).getMonth().getValue());
        response.setYear(date.minusMonths(monthRequest.getPastMonths()).getYear());
        response.setSumIncomes(incomes.stream().mapToDouble(r -> Math.floor(Math.abs(r.getValue()) * 100) / 100).sum());
        response.setSumOutcomes(outcomes.stream().mapToDouble(r -> Math.floor(Math.abs(r.getValue()) * 100) / 100).sum());
        response.setCategorizedIncomes(incomes.stream().collect(Collectors.groupingBy(AccountRecord::getCategory)));
        response.setCategorizedOutcomes(outcomes.stream().collect(Collectors.groupingBy(AccountRecord::getCategory)));
        return response;
    }

    @RequestMapping(value = "/yearly-incomes/{year}", method = RequestMethod.POST)
    public Double[] retrieveAccountRecordsForDateIncomes(@PathVariable Integer year, @RequestBody(required = false) String category, Principal principal) {
        final String cat = category != null ? category : "";
        Double[] values = new Double[12];
        for (Month month : Month.values()) {
            LocalDate start = LocalDate.of(year, month, 1);
            LocalDate end = LocalDate.of(year, month, month.maxLength());
            double sum = Math.floor(accountRecordService.findByDateBetweenAndCategory(start, end, cat, principal.getName()).stream().filter(r -> r.getValue() > 0).mapToDouble(r -> Math.abs(r.getValue())).sum() * 100) / 100;
            values[month.getValue() - 1] = sum;
        }
        return values;
    }

    @RequestMapping(value = "/yearly-outcomes/{year}", method = RequestMethod.POST)
    public Double[] retrieveAccountRecordsForDateOutcomes(@PathVariable Integer year, @RequestBody(required = false) String category, Principal principal) {
        final String cat = category != null ? category : "";
        Double[] values = new Double[12];
        for (Month month : Month.values()) {
            LocalDate start = LocalDate.of(year, month, 1);
            LocalDate end = LocalDate.of(year, month, month.maxLength());
            double sum = Math.floor(accountRecordService.findByDateBetweenAndCategory(start, end, cat, principal.getName()).stream().filter(r -> r.getValue() < 0).mapToDouble(r -> Math.abs(r.getValue())).sum() * 100) / 100;
            values[month.getValue() - 1] = sum;
        }
        return values;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void importCsvFile(@RequestBody MultipartFile file, @RequestParam String fileType, Principal principal) throws IOException, ParseException {
        if (FilenameUtils.isExtension(file.getOriginalFilename(), "csv")) {
            File tempFile = File.createTempFile(AccountManagerApplication.ROOT, ".csv");
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(tempFile));
            FileCopyUtils.copy(file.getInputStream(), stream);
            stream.close();

            accountRecordService.importFile(tempFile, principal.getName(), fileType);
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
