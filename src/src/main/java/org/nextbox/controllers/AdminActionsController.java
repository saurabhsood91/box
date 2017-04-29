package org.nextbox.controllers;

import org.nextbox.managers.PlanManager;
import org.nextbox.managers.UserManager;
import org.nextbox.model.Plan;
import org.nextbox.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by saurabh on 4/23/17.
 */

@Controller
public class AdminActionsController {

    @Autowired
    private HttpSession session;

    @Autowired
    private PlanManager planManager;

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/admin/addplan", method = GET)
    public String createPlan() {
        return "addplan";
    }

    @RequestMapping(value = "/admin/plan/insert", method = RequestMethod.POST)
    public String addPlan(@RequestParam("ratePerGB") String ratePerGB, @RequestParam("maxSpace") String maxSpace, Model model) {

        double rate, space;

        if(ratePerGB.compareTo("") == 0) {
            model.addAttribute("message", "Rate is required to create a plan");
            return "addplan";
        }

        if(maxSpace.compareTo("") == 0) {
            model.addAttribute("Space is a required to create a plan");
            return "addplan";
        }

        // Convert rate to double
        try {
            rate = Double.parseDouble(ratePerGB);
        } catch(NumberFormatException nfe) {
            model.addAttribute("message", "Invalid format for rate. A Double value is required");
            return "addplan";
        }

        // Convert space to double
        try {
            space = Double.parseDouble(ratePerGB);
        } catch(NumberFormatException nfe) {
            model.addAttribute("message", "Invalid format for space. A Double value is required");
            return "addplan";
        }

        // Values are valid. Check if the plan already exists or not
        if(planManager.doesPlanExist(rate, space)) {
            // do not create. set error message
            model.addAttribute("message", "Plan already exists. Please change the attributes");
            return "addplan";
        }
        // Plan does not exist. Create
        boolean planCreated = planManager.createPlan(rate, space);

        if(planCreated) {
            model.addAttribute("message", "Plan Created");
        } else {
            model.addAttribute("message", "Something went wrong in creating plan");
        }
        return "admin_home";
    }

    @RequestMapping(value = "/admin/modifyplan", method = GET)
    public String updatePlan(Model model) {
        List plans = planManager.getAllPlans();
        model.addAttribute("plans", plans);
        return "modifyplan";
    }

    @RequestMapping(value = "/admin/plan/details", method = GET)
    public String planDetails(@RequestParam("id") String id, Model model) {
        // Get plan details by ID:
        Plan plan = planManager.getPlanById(id);
        model.addAttribute("rate", plan.getRate());
        model.addAttribute("space", plan.getSpace());
        model.addAttribute("id", plan.getId());
        return "updateplan";
    }

    @RequestMapping(value = "/admin/plan/modify", method = RequestMethod.POST)
    public String modifyPlan(@RequestParam("id") String id, @RequestParam("rate") String space, @RequestParam("rate") String rate, Model model) {
        planManager.modifyPlan(id, rate, space);
        model.addAttribute("message", "Plan Modified");
        return "admin_home";
    }


    @RequestMapping(value = "/admin/changeActivationStatus", method = GET)
    public String changeActivationStatus() {
        return "changeActivationStatus";
    }

    @RequestMapping(value = "/admin/changeActivationStatus/changeStatus", method = RequestMethod.POST)
    public String modifyActivationStatus(@RequestParam("username") String username, @RequestParam("activationStatus") String activationStatus, Model model) {
        User user = (User)session.getAttribute("user");
        if(userManager.getUserByUsername(username) == null)
        {
            model.addAttribute("message", "username invalid");
            return "changeActivationStatus";
        }
        if(user.getUserName().compareTo(username) == 0)
        {
            model.addAttribute("message", "username same as logged in, invalid operation");
            return "changeActivationStatus";
        }

        userManager.modifyActivationStatus(username,activationStatus);
        model.addAttribute("message", "status changed");
        return "admin_home";
    }

    @RequestMapping(value = "/admin/createAdmin", method = GET)
    public String createAdmin(Model model){
        List plans = planManager.getAllPlanObjects();
        model.addAttribute("plans", plans);
        return "createAdmin";
    }

    @RequestMapping(value = "/createAdmin", method = RequestMethod.POST, params = {"firstname","lastname","email",
            "username", "password","selectedPlan"})
    public String createAdminAccount(@RequestParam(value="firstname") String firstName,@RequestParam(value="lastname")String lastName,
                                     @RequestParam(value="email") String email,@RequestParam(value="username") String userName,
                                     @RequestParam(value="password")String password, @RequestParam(value="selectedPlan")String planId ,Model model) {
        boolean success = false;
        try {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setUserName(userName);
            user.setPassword(password);
            user.setPlan(planManager.getPlanById(planId));
            user.setActivation_status("active");
            user.setRole("admin");
            String msg = userManager.createAdminAccount(user);
            if (!msg.startsWith("Oops")) success = true;
            model.addAttribute("message", msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success ? "admin_home" : "createAdmin";
    }
}
