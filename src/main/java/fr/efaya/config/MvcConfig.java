package fr.efaya.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by KTIFA FAMILY on 11/05/2016.
 */
//@Controller
/*@EnableWebMvc*/
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
    }

    /*@RequestMapping(value="/partials/{partial}.html", method = RequestMethod.GET)
    public String getPartials(@PathVariable String partial){
        return partial;
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("templates/partials/");
        return resolver;
    }*/
}
