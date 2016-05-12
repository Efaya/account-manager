package fr.efaya;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by KTIFA FAMILY on 28/03/2016.
 */
@Controller
@RequestMapping("/partials")
public class AccountRecordViewController {

    @RequestMapping(value = "{partial}.html", method = RequestMethod.GET)
    public String getPartials(@PathVariable String partial){
        return "/templates/partials/" + partial + ".html";
    }

    /*@Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("templates/partials/");
        return resolver;
    }
*/
}
