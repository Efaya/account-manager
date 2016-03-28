package fr.efaya;

import fr.efaya.database.AccountRecordsRepository;
import fr.efaya.domain.AccountRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private AccountRecordsRepository accountRecordsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<AccountRecord> retrieveAccountRecords() {
        return accountRecordsRepository.findAll();
    }

    @RequestMapping(value = "/monthly", method = RequestMethod.GET)
    public List<AccountRecord> retrieveAccountRecordsForDate(Date refDate) {
        LocalDate date = refDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate start = date.withDayOfMonth(1);
        LocalDate end = date.withDayOfMonth(date.lengthOfMonth());
        return accountRecordsRepository.findByDateBetween(Date.from(Instant.from(start.atStartOfDay())), Date.from(Instant.from(end.atStartOfDay())));
    }

    @RequestMapping(method = RequestMethod.POST)
    public AccountRecord insertAccountRecord(@RequestBody AccountRecord record) {
        return accountRecordsRepository.insert(record);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public AccountRecord updateAccountRecord(@PathVariable String id, @RequestBody AccountRecord record) {
        return accountRecordsRepository.save(record);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeAccountRecord(@PathVariable String id) {
        accountRecordsRepository.delete(id);
    }
}
