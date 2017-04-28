package org.nextbox.managers;

import org.nextbox.beans.NextboxProperties;
import org.nextbox.dao.UserDAO;
import org.nextbox.model.User;
import org.nextbox.service.FilesystemAPI;
import org.nextbox.service.FilesystemService;
import org.nextbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabh on 3/21/17.
 */
@Service("userManager")
public class UserManager {
    private static List<String> existingUser = null;
    @Autowired
    private UserService userService;

    @Autowired
    private NextboxProperties props;

    @Autowired
    private UserDAO userDAO;

    public User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    public boolean createAccount(User user,Model model) throws FileNotFoundException {
//        user.setHomeDirectory("home");
//        user.setRole("user");

//        System.out.println(props.getUploadDir());
        String baseDir = props.getUploadDir();

        Path basePath = Paths.get(baseDir);
        Path homeDir = Paths.get(baseDir, user.getUserName());
        FilesystemAPI.createDir(user, basePath, user.getUserName());
        user.setHomeDirectory(homeDir.toAbsolutePath().toString());

        if (existingUser == null || existingUser.isEmpty()){
            initExistingUsers();
        }
        boolean isUserCreated = false;
        if (existingUser.contains(user.getUserName())){
            model.addAttribute("message", "Oops!! Username taken. Please get creative");
        }else {
            isUserCreated = userService.createAccount(user);
            if(!isUserCreated) model.addAttribute("message", "Oops!! System in limbo.Please try after sometime");
        }
        return isUserCreated;
    }

    private void initExistingUsers() {
        existingUser = userService.getExistingUsers();
    }

    public boolean modifyActivationStatus (String username, String activationStatus)
    {
        return userDAO.modifyActivationStatus(username,activationStatus);
    }

}