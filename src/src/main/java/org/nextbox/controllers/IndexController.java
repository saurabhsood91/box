package org.nextbox.controllers;
import org.nextbox.model.AbstractUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

/**
 * Created by saurabh on 3/19/17.
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        model.addAttribute("org", "NextBox");

        AbstractUser user = (AbstractUser)session.getAttribute("user");
        if(user != null) {
            model.addAttribute("user", user);
            return "loginsuccess";
        }

        return "index";
    }
}
