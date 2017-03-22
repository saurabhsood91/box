package org.nextbox.dao;

import org.nextbox.model.AbstractUser;

/**
 * Created by saurabh on 3/21/17.
 */
public interface UserDAO {
    public AbstractUser getUserByUsername(String string);
}
