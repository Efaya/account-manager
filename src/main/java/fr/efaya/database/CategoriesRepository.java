package fr.efaya.database;

import fr.efaya.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by KTIFA FAMILY on 28/03/2016.
 */
@Repository
public interface CategoriesRepository extends MongoRepository<Category, String> {
}
