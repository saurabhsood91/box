package org.nextbox.controllers;
import org.nextbox.model.User;
import org.nextbox.service.FilesystemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.io.File;
import java.nio.file.Path;

import javax.servlet.http.HttpSession;

/**
 * Created by saurabh on 3/19/17.
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        model.addAttribute("org", "NextBox");

        User user = (User)session.getAttribute("user");
        if(user != null) {
            model.addAttribute("user", user);

            // Get home directory
            Path homeDirectory = user.getHomeDirectory();
            File[] homeDirectoryContents = FilesystemService.getDirContents(homeDirectory);

            model.addAttribute("files", homeDirectoryContents);
            model.addAttribute("currentDirectory", homeDirectory.toString());

            return "home";
        }

        return "index";
    }
}
