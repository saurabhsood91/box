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
import java.nio.file.Path;
import javax.servlet.http.HttpSession;

import org.nextbox.service.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

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
            // add user object to model
            model.addAttribute("user", user);
            if(!user.isActive())
            {
                model.addAttribute("message", "Inactive username. Contact administrator to reactivate user.");
                return "index";
            }

            httpSession.setAttribute("user", user);

            if(!user.isAdmin()) {
                model.addAttribute("currentDirectory", user.getHomeDirectory());

                // Get home directory
                Path homeDirectory = user.getHomeDirectory();
                File[] homeDirectoryContents = FilesystemService.getDirContents(homeDirectory);

                model.addAttribute("files", homeDirectoryContents);

                model.addAttribute("message", "Logged in!");
                return "home";
            } else {
                // TODO return some other page
                model.addAttribute("message", "Administration Panel");
                return "admin_home";
            }

        }
        model.addAttribute("message", "Invalid username or password!");
        return "index";
    }

    @RequestMapping(value = "/finduser", method = RequestMethod.GET)
    public String findUser(@RequestParam("usernameToSearch") String usernameToSearch, @RequestParam("fileToShare") String fileToShare, Model model) {
        User userToShare = userManager.getUserByUsername(usernameToSearch);
        if(userToShare != null) {
            model.addAttribute("userToShare", userToShare.getUserName());
        } else {
            model.addAttribute("message", "No Username found");
        }
        model.addAttribute("fileToShare", fileToShare);
        return "sharefile";
    }

}
