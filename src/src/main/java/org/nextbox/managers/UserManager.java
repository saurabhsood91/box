package org.nextbox.managers;

import org.nextbox.beans.NextboxProperties;
import org.nextbox.model.User;
import org.nextbox.service.FilesystemAPI;
import org.nextbox.service.FilesystemService;
import org.nextbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by saurabh on 3/21/17.
 */
@Service("userManager")
public class UserManager {

    @Autowired
    private UserService userService;

    @Autowired
    private NextboxProperties props;

    public User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    public boolean createAccount(User user) throws FileNotFoundException {
//        user.setHomeDirectory("home");
//        user.setRole("user");

//        System.out.println(props.getUploadDir());
        String baseDir = props.getUploadDir();

        Path basePath = Paths.get(baseDir);

        Path homeDir = Paths.get(baseDir, user.getUserName());
        FilesystemAPI.createDir(user, basePath, user.getUserName());
        user.setHomeDirectory(homeDir.toAbsolutePath().toString());

        return userService.createAccount(user);
    }
}
