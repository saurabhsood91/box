package org.nextbox.service;

import org.nextbox.dao.UserDAO;
import org.nextbox.model.AbstractUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by saurabh on 3/21/17.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    public AbstractUser getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }
}
