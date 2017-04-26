package org.nextbox.controllers;

/**
 * Created by saurabh on 3/19/17.
 */

import org.nextbox.managers.PlanManager;
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

@Controller
public class UserAccountController {

    @Autowired
    public LoginService loginService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserManager userManager;

    @Autowired
    private PlanManager planManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST, params = {"username", "password"})
    public String login(@RequestParam(value="username") String username, @RequestParam(value="password") String password, Model model) {
        boolean isLogin = loginService.checkLogin(username, password);
        if(isLogin) {

            // Get username
            User user = userManager.getUserByUsername(username);
            // add user object to model
            model.addAttribute("user", user);

            if(!user.isAdmin()) {
                httpSession.setAttribute("user", user);

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

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST, params = {"firstname","lastname","email",
                "username", "password","plan"})
    public String createAccount(@RequestParam(value="firstname") String firstName,@RequestParam(value="lastname")String lastName,
                                 @RequestParam(value="email") String email,@RequestParam(value="username") String userName,
                                 @RequestParam(value="password")String password, @RequestParam(value="plan")String plan,Model model){
        boolean success = false;
        try{
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setUserName(userName);
            user.setPassword(password);
            user.setPlan(planManager.getPlanById(plan));
            user.setRole("user");
            success = userManager.createAccount(user,model);
        }catch (Exception e){
            e.printStackTrace();
        }
        return success ? "home" : "signup";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String  signup(){
        return "signup";
    }

}
