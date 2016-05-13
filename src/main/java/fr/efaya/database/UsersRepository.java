package fr.efaya.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 * Created by KTIFA FAMILY on 28/03/2016.
 */
@Repository
public interface UsersRepository extends MongoRepository<UserDetails, String> {
}
