package org.nextbox.controllers;

/**
 * Created by saurabh on 3/19/17.
 */

import org.nextbox.managers.UserManager;
import org.nextbox.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import javax.servlet.http.HttpSession;

import org.nextbox.service.*;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserAccountController {

    @Autowired
    public LoginService loginService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST, params = {"username", "password"})
    public String login(@RequestParam(value="username") String username, @RequestParam(value="password") String password, Model model) {
        boolean isLogin = loginService.checkLogin(username, password);
        if(isLogin) {

            // Get username
            User user = userManager.getUserByUsername(username);
            if(!user.isAdmin()) {
                httpSession.setAttribute("user", user);

                // add user object to model
                model.addAttribute("user", user);
                model.addAttribute("currentDirectory", user.getHomeDirectory());

                // Get home directory
                String homeDirectory = user.getHomeDirectory();
                File[] homeDirectoryContents = FilesystemService.getDirContents(homeDirectory);

                model.addAttribute("files", homeDirectoryContents);

                model.addAttribute("message", "Logged in!");
                return "home";
            } else {
                // TODO return some other page
            }

        }
        model.addAttribute("message", "Invalid username or password!");
        return "index";
    }

}
