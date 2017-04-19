package org.nextbox.dao;

import org.nextbox.model.User;

/**
 * Created by saurabh on 3/21/17.
 */
public interface UserDAO {
    public User getUserByUsername(String string);

    public boolean createAccount(User user);
}
