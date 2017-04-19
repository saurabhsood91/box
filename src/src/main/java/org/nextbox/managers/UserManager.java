package org.nextbox.managers;

import org.nextbox.model.User;
import org.nextbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by saurabh on 3/21/17.
 */
@Service("userManager")
public class UserManager {

    @Autowired
    private UserService userService;

    public User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    public boolean createAccount(User user) {
        user.setHomeDirectory("home");
//        user.setRole("user");
        return userService.createAccount(user);
    }
}
