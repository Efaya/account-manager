package fr.efaya;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Created by KTIFA FAMILY on 27/05/2016.
 */
@Controller
@RequestMapping("partials")
public class PartialViewController {

    @RequestMapping(path = "{partialId}", method = RequestMethod.GET)
    public ModelAndView getHomePartialPage(@PathVariable String partialId, Principal principal) {
        ModelAndView mav = new ModelAndView("partials/" + partialId, "principal", principal.getName());
        mav.addObject("authorities", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return mav;
    }
}
