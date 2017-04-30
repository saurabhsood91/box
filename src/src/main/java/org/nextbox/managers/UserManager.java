package org.nextbox.managers;

import org.nextbox.beans.NextboxProperties;
import org.nextbox.dao.UserDAO;
import org.nextbox.model.Filepath;
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
    public Integer[] n = {1,2,4,5,6};
    public User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    public String createAccount(User user) throws FileNotFoundException {
//        user.setHomeDirectory("home");
//        user.setRole("user");

//        System.out.println(props.getUploadDir());
        String baseDir = props.getUploadDir();
        String responseMsg = "Welcome to NextBox " + user.getUserName();
        Path basePath = Paths.get(baseDir);
        Path homeDir = Paths.get(baseDir, user.getUserName());

        Path newPath = Paths.get(basePath.toString(), user.getUserName());

        FilesystemAPI.createdir(newPath);
        user.setHomeDirectory(homeDir.toAbsolutePath().toString());

        if (existingUser == null || existingUser.isEmpty()){
            initExistingUsers();
        }
        boolean isUserCreated = false;
        if (existingUser.contains(user.getUserName())){
            responseMsg = "Oops!! Username taken. Please get creative";
        }else {
            isUserCreated = userService.createAccount(user);
            if(!isUserCreated) responseMsg = "Oops!! System in limbo.Please try after sometime";
        }
        return responseMsg;
    }

    private void initExistingUsers() {
        existingUser = userService.getExistingUsers();
    }

    public boolean modifyActivationStatus (String username, String activationStatus)
    {
        return userDAO.modifyActivationStatus(username,activationStatus);
    }

    public boolean addCreditCardDetails(String userId, String cardDetails){
        return userDAO.updateCreditCardDetails(Long.parseLong(userId),cardDetails);
    }

    public String createAdminAccount(User user) throws FileNotFoundException {
//        user.setHomeDirectory("home");
//        user.setRole("user");

//        System.out.println(props.getUploadDir());
        String baseDir = props.getUploadDir();
        String responseMsg = "Welcome to NextBox " + user.getUserName();
        Path basePath = Paths.get(baseDir);
        Path homeDir = Paths.get(baseDir, user.getUserName());

        Path newPath = Paths.get(basePath.toString(), user.getUserName());

        FilesystemAPI.createdir(newPath);
        user.setHomeDirectory(homeDir.toAbsolutePath().toString());

        if (existingUser == null || existingUser.isEmpty()){
            initExistingUsers();
        }
        boolean isUserCreated = false;
        if (existingUser.contains(user.getUserName())){
            responseMsg = "Oops!! Username taken. Please get creative";
        }else {
            isUserCreated = userService.createAccount(user);
            if(!isUserCreated) responseMsg = "Oops!! System in limbo.Please try after sometime";
        }
        return responseMsg;
    }
}