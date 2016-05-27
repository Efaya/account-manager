package fr.efaya.register;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by KTIFA FAMILY on 27/05/2016.
 */
@Controller
public class ErrorHandlerController {

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView getLoginPage() {
        return new ModelAndView("error");
    }
}
