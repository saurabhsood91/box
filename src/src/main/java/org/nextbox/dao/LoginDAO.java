package org.nextbox.dao;

/**
 * Created by saurabh on 3/19/17.
 */

import org.nextbox.model.AbstractUser;

public interface LoginDAO {
    public boolean checkLogin(String userName, String password);
}
