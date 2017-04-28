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
import java.util.List;
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
        if (userToShare != null) {
            model.addAttribute("userToShare", userToShare.getUserName());
        } else {
            model.addAttribute("message", "No Username found");
        }
        model.addAttribute("fileToShare", fileToShare);
        return "sharefile";
    }

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST, params = {"firstname","lastname","email",
                "username", "password","selectedPlan"})
    public String createAccount(@RequestParam(value="firstname") String firstName,@RequestParam(value="lastname")String lastName,
                                 @RequestParam(value="email") String email,@RequestParam(value="username") String userName,
                                 @RequestParam(value="password")String password, @RequestParam(value="selectedPlan")String planId ,Model model){
        boolean success = false;
        try{
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setUserName(userName);
            user.setPassword(password);
            user.setPlan(planManager.getPlanById(planId));
            user.setActivation_status("active");
            user.setRole("user");
            String msg = userManager.createAccount(user);
            if (!msg.startsWith("Oops")) success = true;
            model.addAttribute("message",msg);
        }catch (Exception e){
            e.printStackTrace();
        }
        return success ? "home" : "signup";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String  signup(Model model){

        List plans = planManager.getAllPlanObjects();
        model.addAttribute("plans", plans);

        return "signup";
    }



    @RequestMapping(value = "/saveCreditCard", method = RequestMethod.POST, params = {"cardNumber","nameOnCard","monthOfTheYear","yearExpiry","userId"})
    public String addCard(@RequestParam(value="cardNumber") String cardNumber, @RequestParam(value="nameOnCard")
            String nameOnCard,@RequestParam(value="monthOfTheYear") String monthOfExpiry, @RequestParam(value="yearExpiry")String yearOfExpiry,@RequestParam(value="userId")String userId,Model model) {
        String s = "|";
        String creditCardDetails = cardNumber + s + nameOnCard + s + monthOfExpiry + s + yearOfExpiry + s;
        boolean updated = userManager.addCreditCardDetails(userId, creditCardDetails);
        String msg = "Unbale to update or add credit card.Please try later";
        if (updated) {
            msg = "Successfully updated credit card details";
        }
        model.addAttribute("message",msg);
        return "home";
    }
}