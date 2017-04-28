package org.nextbox.service;

import org.nextbox.model.User;

import java.util.List;

/**
 * Created by saurabh on 3/21/17.
 */
public interface UserService {
    public User getUserByUsername(String username);

    public boolean createAccount(User user);
    public List<String> getExistingUsers();
}
