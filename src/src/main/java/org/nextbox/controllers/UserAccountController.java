package org.nextbox.controllers;

/**
 * Created by saurabh on 3/19/17.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import javax.validation.Valid;

import org.nextbox.service.*;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserAccountController {

    @Autowired
    public LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, params = {"username", "password"})
    public String login(@RequestParam(value="username") String username, @RequestParam(value="password") String password) {
        boolean isLogin = loginService.checkLogin(username, password);
        if(isLogin) {
            return "loginsuccess";
        } else {
            return "loginfail";
        }
    }

}
