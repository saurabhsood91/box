package org.nextbox.controllers;

import javafx.scene.shape.Path;
import org.nextbox.model.User;
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
import java.nio.file.FileSystems;

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
        User user = (User)session.getAttribute("user");

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
        model.addAttribute("currentDirectory", currentDirectory);
        return "home";
    }

    @RequestMapping(value="/createDir")
    public String createDir(@RequestParam("dirName")String dirName, @RequestParam("currentDirectory")String currentDirectory, Model model) throws FileNotFoundException {

        // Get session object
        User user = (User)session.getAttribute("user");

        boolean created = FilesystemAPI.createDir(user, currentDirectory, dirName);

        if(created) {
            model.addAttribute("message", "Directory successfully created");
            // Get home directory
            String homeDirectory = user.getHomeDirectory();
            java.io.File[] homeDirectoryContents = FilesystemService.getDirContents(homeDirectory);

            model.addAttribute("files", homeDirectoryContents);
        } else {
            model.addAttribute("message", "Failed to create directory");
        }
        return "home";
    }
}
