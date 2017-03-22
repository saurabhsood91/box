package org.nextbox.service;

import org.nextbox.model.AbstractUser;

/**
 * Created by saurabh on 3/21/17.
 */
public interface UserService {
    public AbstractUser getUserByUsername(String username);
}
