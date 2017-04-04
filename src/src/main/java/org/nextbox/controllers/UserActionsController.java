package org.nextbox.controllers;

import org.nextbox.model.AbstractUser;
import org.nextbox.model.File;
import org.nextbox.service.FilesystemAPI;
import org.nextbox.service.FilesystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;

/**
 * Created by saurabh on 3/27/17.
 */

@Controller
public class UserActionsController {

    @Autowired
    private HttpSession session;

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("uploadedfile")MultipartFile file, @RequestParam("currentDirectory")String currentDirectory, Model model) throws FileNotFoundException {
        // Create a file object
        File fileToUpload = new File(file);

        // Get session object
        AbstractUser user = (AbstractUser)session.getAttribute("user");

        boolean uploaded = FilesystemAPI.uploadFile(user, fileToUpload, currentDirectory);

        if(uploaded) {
            model.addAttribute("message", "File successfully uploaded");
            // Get home directory
            String homeDirectory = user.getHomeDirectory();
            java.io.File[] homeDirectoryContents = FilesystemService.getDirContents(homeDirectory);

            model.addAttribute("files", homeDirectoryContents);
        } else {
            model.addAttribute("message", "Failed to upload file");
        }
        return "home";
    }
}
