package org.nextbox.service;

/**
 * Created by saurabh on 3/19/17.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.nextbox.dao.LoginDAO;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    public void setLoginDAO(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    @Autowired
    private LoginDAO loginDAO;

    public boolean checkLogin(String userName, String userPassword) {
        return loginDAO.checkLogin(userName, userPassword);
    }
}
