package org.nextbox.controllers;

import org.nextbox.model.File;
import org.nextbox.model.Filepath;
import org.nextbox.model.User;
import org.nextbox.service.FilesystemAPI;
import org.nextbox.service.FilesystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        Filepath nPath = new Filepath();
        nPath.setPath(currentDirectory);
        Path currentDir = nPath.getPath();

        boolean uploaded = FilesystemAPI.uploadFile(user, fileToUpload, currentDir);

        if(uploaded) {
            model.addAttribute("message", "File successfully uploaded");
            // Get home directory
            Path homeDirectory = user.getHomeDirectory();
            java.io.File[] homeDirectoryContents = FilesystemService.getDirContents(homeDirectory);

            model.addAttribute("files", homeDirectoryContents);
        } else {
            model.addAttribute("message", "Failed to upload file");
        }

        model.addAttribute("currentDirectory", currentDirectory);
        return "home";
    }

    @RequestMapping(value="/search", method = RequestMethod.POST)
    public String searchFile(@RequestParam("searchTerm")String searchTerm, @RequestParam("currentDirectory")String currentDirectory, Model model){

        java.io.File[] results = FilesystemAPI.searchFile(searchTerm, currentDirectory);
        model.addAttribute("searchResults",results);
        model.addAttribute("currentDirectory",currentDirectory);
        model.addAttribute("searchTerm", searchTerm);

        return "home";
    }

    @RequestMapping(value="/returnToHome", method = RequestMethod.POST)
    public String returnToHome(@RequestParam("currentDirectory")String currentDirectory, Model model){
        // Get session object
        User user = (User)session.getAttribute("user");
        Path homeDirectory = user.getHomeDirectory();
        java.io.File[] homeDirectoryContents = FilesystemService.getDirContents(homeDirectory);

        model.addAttribute("currentDirectory",currentDirectory);
        model.addAttribute("files", homeDirectoryContents);

        return "home";
    }

    @RequestMapping(value="/createDir")
    public String createDir(@RequestParam("createDirName")String dirName, @RequestParam("currentDirectory")String currentDirectory, Model model) throws FileNotFoundException {

        // Get session object
        User user = (User)session.getAttribute("user");
        // Get current directory
        Path homeDirectory = user.getHomeDirectory();

        Filepath currentDir = new Filepath();
        currentDir.setPath(currentDirectory);
        Filepath newDir = new Filepath();
        newDir.setPath(dirName);
        boolean created = FilesystemAPI.createDir(user, newDir);

        if(created) {
            model.addAttribute("message", "Directory successfully created");
        }
        else {
            model.addAttribute("message", "Failed to create directory");
        }

        java.io.File[] directoryContents = FilesystemService.getDirContents(currentDir.getPath());
        System.out.println(newDir.toAbs().toString());
        model.addAttribute("files", directoryContents);
        model.addAttribute("currentDirectory", currentDirectory);
        return "home";
    }

    @RequestMapping(value="/view")
    public String View(@RequestParam("currentDirectory")String currentDirectory, @RequestParam("fileSelected")String fileSelected, Model model){
        Filepath nPath = new Filepath();
        nPath.setPath(fileSelected);
        Path newDir = nPath.toAbs();
        java.io.File[] directoryContents = FilesystemService.getDirContents(newDir);

        User user = (User)session.getAttribute("user");

        model.addAttribute("fileSelected", newDir);
        model.addAttribute("files", directoryContents);

        return "home";
    }

    @RequestMapping(value="/download")
    public void download(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestParam("fileSelected")String fileSelected, @RequestParam("currentDirectory")String currentDirectory, Model model) throws FileNotFoundException {

        // Get session object
        User user = (User) session.getAttribute("user");
        boolean downloaded =  FilesystemAPI.download(user,fileSelected,response);
    }
}
