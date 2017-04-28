package org.nextbox.controllers;

import org.nextbox.model.File;
import org.nextbox.managers.UserManager;
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
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by saurabh on 3/27/17.
 */

@Controller
public class UserActionsController {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserManager userManager;

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
        Filepath nPath = new Filepath();
        nPath.setPath(currentDirectory);
        Path currentDir = nPath.getPath();
        java.io.File[] homeDirectoryContents = FilesystemService.getDirContents(currentDir);

        User user = (User)session.getAttribute("user");

        model.addAttribute("currentDirectory",currentDirectory);
        model.addAttribute("files", homeDirectoryContents);

        return "home";
    }

    @RequestMapping(value="/createDir")
    public String createDir(@RequestParam("createDirName")String dirName, @RequestParam("currentDirectory")String currentDirectory, Model model) throws FileNotFoundException {

        // Get session object
        User user = (User)session.getAttribute("user");

        Filepath nPath = new Filepath();
        nPath.setPath(currentDirectory);
        Path currentDir = nPath.getPath();

        boolean created = FilesystemAPI.createDir(user, currentDir, dirName);

        if(created) {
            model.addAttribute("message", "Directory successfully created");
        }
        else {
            model.addAttribute("message", "Failed to create directory");
        }
        // Get home directory
        Path homeDirectory = user.getHomeDirectory();
        java.io.File[] homeDirectoryContents = FilesystemService.getDirContents(homeDirectory);

        model.addAttribute("files", homeDirectoryContents);
        model.addAttribute("currentDirectory", currentDirectory);
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

    @RequestMapping(value = "/sharefile", method = RequestMethod.GET)
    public String shareFile(@RequestParam("fileToShare") String fileToShare, Model model) {
        // Get the user from the sesion
        User user = (User)session.getAttribute("user");
        model.addAttribute("fileToShare", fileToShare);
        return "sharefile";
    }

    @RequestMapping(value="/share", method=RequestMethod.POST)
    public String share(@RequestParam("fileToShare") String fileToShare, @RequestParam("userToShare") String userToShare, Model model) throws IOException {
        User user = (User)session.getAttribute("user");
        User secondUser = userManager.getUserByUsername(userToShare);
        user.shareFile(fileToShare, secondUser);

        // Set model properties
        model.addAttribute("user", user);
        model.addAttribute("currentDirectory", user.getHomeDirectory());

        // Get home directory
        Path homeDirectory = user.getHomeDirectory();
        java.io.File[] homeDirectoryContents = FilesystemService.getDirContents(homeDirectory);

        model.addAttribute("files", homeDirectoryContents);
        model.addAttribute("message", "File shared with " + userToShare);

        return "home";
    }
    @RequestMapping(value="/viewUsage")
    public String viewUsage(@RequestParam("userName")String userName, Model model)  {
        User user = (User)session.getAttribute("user");
        Path homeDirectory = user.getHomeDirectory();

        model.addAttribute("maximumAvailableSpace", "placeholder");
        model.addAttribute("usedSpace",FilesystemAPI.getUsedSpace(homeDirectory));
        model.addAttribute("freeSpace", "placeholder");
        return "viewUsage";
    }
}
