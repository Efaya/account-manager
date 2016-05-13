package fr.efaya.database;

import fr.efaya.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by KTIFA FAMILY on 28/03/2016.
 */
@Repository
public interface UsersRepository extends MongoRepository<User, String> {
    User findByUsername(String name);
}
