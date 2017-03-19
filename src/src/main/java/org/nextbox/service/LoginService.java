package org.nextbox.service;

/**
 * Created by saurabh on 3/19/17.
 */
import org.nextbox.model.AbstractUser;

public interface LoginService {
    public boolean checkLogin(String userName, String userPassword);
}
