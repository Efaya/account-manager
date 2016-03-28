package fr.efaya.database;

import fr.efaya.domain.AccountRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by KTIFA FAMILY on 28/03/2016.
 */
@Repository
public interface AccountRecordsRepository extends MongoRepository<AccountRecord, String> {
    List<AccountRecord> findByDateBetween(Date from, Date to);
}
