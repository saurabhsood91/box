package org.nextbox.controllers;

import org.nextbox.managers.PlanManager;
import org.nextbox.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by saurabh on 4/23/17.
 */

@Controller
public class AdminActionsController {

    @Autowired
    private HttpSession session;

    @Autowired
    private PlanManager planManager;

    @RequestMapping(value = "/admin/addplan", method = RequestMethod.GET)
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
}
