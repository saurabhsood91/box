package org.nextbox.controllers;

import org.nextbox.managers.PlanManager;
import org.nextbox.model.File;
import org.nextbox.managers.UserManager;
import org.nextbox.model.Filepath;
import org.nextbox.model.Plan;
import org.nextbox.model.User;
import org.nextbox.service.BillingService;
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
import java.nio.file.FileSystem;
import java.nio.file.Path;

/**
 * Created by saurabh on 3/27/17.
 */

@Controller
public class UserActionsController {

    @Autowired
    private PlanManager planManager;

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

        //Filepath currentDir = new Filepath();
        //currentDir.setPath(currentDirectory);
        //System.out.println(homeDirectory.toAbsolutePath().relativize(Paths.get(dirName).toAbsolutePath()).toString());
        Filepath newDir = new Filepath();
        newDir.setPath(dirName);
        boolean created = FilesystemAPI.createDir(user, newDir);

        if(created) {
            model.addAttribute("message", "Directory successfully created");
        }
        else {
            model.addAttribute("message", "Failed to create directory");
        }

        java.io.File[] directoryContents = FilesystemService.getDirContents(homeDirectory);
        model.addAttribute("files", directoryContents);
        model.addAttribute("currentDirectory", currentDirectory);
        return "home";
    }

    @RequestMapping(value="/view")
    public String View(@RequestParam("currentDirectory")String currentDirectory, @RequestParam("fileSelected")String fileSelected, Model model){
        Filepath nPath = new Filepath();
        nPath.setPath(fileSelected);

        if (nPath.pathIsDir()) {
            Path newDir = nPath.toAbs();
            java.io.File[] directoryContents = FilesystemService.getDirContents(newDir);

            User user = (User) session.getAttribute("user");
            model.addAttribute("searchResults",null);
            model.addAttribute("files", directoryContents);

            return "home";
        }
        else {
            return "home";
        }
    }

    @RequestMapping(value="/delete")
    public String Delete(@RequestParam("fileSelected")String fileSelected, Model model) throws FileNotFoundException {
        // Get session object
        User user = (User)session.getAttribute("user");
        // Get current directory
        Path homeDirectory = user.getHomeDirectory();
        Filepath delPath = new Filepath();
        delPath.setPath(fileSelected);

        boolean deleted = FilesystemAPI.deleteFP(user, delPath);

        if(deleted) {
            model.addAttribute("message", "Object successfully deleted");
        }
        else {
            model.addAttribute("message", "Failed to delete object");
        }

        java.io.File[] directoryContents = FilesystemService.getDirContents(homeDirectory);
        model.addAttribute("files", directoryContents);
        //model.addAttribute("currentDirectory", currentDirectory);
        return "home";
    }

    @RequestMapping(value="/move")
    public String Move(@RequestParam("fileSelected")String fileSelected, Model model) throws IOException {
        // Get session object
        User user = (User) session.getAttribute("user");
        // Get current directory
        Path homeDirectory = user.getHomeDirectory();
        Filepath sourcePath = new Filepath();
        sourcePath.setPath(fileSelected);

        boolean moved = FilesystemAPI.moveRN(user, sourcePath);

        if (moved) {
            model.addAttribute("message", "Object successfully moved");
        } else {
            model.addAttribute("message", "Failed to move object");
        }

        java.io.File[] directoryContents = FilesystemService.getDirContents(homeDirectory);
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

        model.addAttribute("maximumAvailableSpace", FilesystemAPI.getMaximumAvailableSpace(user));
        model.addAttribute("usedSpace",FilesystemAPI.getUsedSpace(homeDirectory));
        model.addAttribute("freeSpace", FilesystemAPI.getFreeSpace(homeDirectory,user));
        return "viewUsage";
    }

    @RequestMapping(value="/userChangePlan")
    public String userChangePlan(Model model) {
        User user = (User) session.getAttribute("user");
        long id = user.getId();
        Plan plan = planManager.getPlanById(String.valueOf(id));
        model.addAttribute("plan", plan);
        model.addAttribute("rate", String.valueOf(plan.getRate()));
        model.addAttribute("space", String.valueOf(plan.getSpace()));
        return "changeplan";
    }

    @RequestMapping(value="/userPlanChange", method = RequestMethod.POST)
    public String userModifyPlan(@RequestParam("space") String space, Model model) {
        User user = (User) session.getAttribute("user");
        long id = user.getId();
        Plan plan = planManager.getPlanById(String.valueOf(id));
        Double rate = plan.getRate();
        planManager.modifyPlan(String.valueOf(id), String.valueOf(rate), space);
        model.addAttribute("message", "Plan Modified");
        return "home";
    }
    @RequestMapping(value = "/creditcard", method = RequestMethod.POST,params = {"userId"})
    public String creditCard(@RequestParam("userId") String userId,Model model){
        model.addAttribute("userId",userId);
        return "credit_card";
    }

    @RequestMapping(value = "/viewbill", method=RequestMethod.GET)
    public String viewBill(Model model) {
        User user = (User) session.getAttribute("user");
        String bill = BillingService.calculateBillForUser(user);
        model.addAttribute("bill", bill);
        model.addAttribute("currentDirectory", user.getHomeDirectory().toString());
        return "bill";
    }

}






