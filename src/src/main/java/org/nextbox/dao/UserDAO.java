package org.nextbox.dao;

import org.nextbox.model.User;

import java.util.List;

/**
 * Created by saurabh on 3/21/17.
 */
public interface UserDAO {
    public User getUserByUsername(String string);
<<<<<<< HEAD
    public boolean modifyActivationStatus(String userName, String activationStatus);
=======

    public boolean createAccount(User user);

    public List<String> getExistingUsernames();
>>>>>>> ani
}
