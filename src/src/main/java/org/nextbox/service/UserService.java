package org.nextbox.service;

import org.nextbox.model.User;

/**
 * Created by saurabh on 3/21/17.
 */
public interface UserService {
    public User getUserByUsername(String username);

    public boolean createAccount(User user);
}
