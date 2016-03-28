package fr.efaya;

import fr.efaya.database.CategoriesRepository;
import fr.efaya.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by KTIFA FAMILY on 28/03/2016.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> retrieveCategories() {
        return categoriesRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Category insertAccountRecord(@RequestBody Category category) {
        return categoriesRepository.insert(category);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Category updateAccountRecord(@PathVariable String id, @RequestBody Category category) {
        return categoriesRepository.save(category);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeCategory(@PathVariable String id) {
        categoriesRepository.delete(id);
    }
}
