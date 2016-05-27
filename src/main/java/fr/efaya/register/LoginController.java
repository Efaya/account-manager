package fr.efaya.register;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

/**
 * Created by KTIFA FAMILY on 27/05/2016.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error, @RequestParam Optional<String> logout) {
        ModelAndView mav = new ModelAndView("login", "error", error);
        mav.addObject("logout", logout);
        return mav;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView getLoginPage(Principal principal) {
        ModelAndView mav = new ModelAndView("index", "principal", principal.getName());
        mav.addObject("authorities", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return mav;
    }

}