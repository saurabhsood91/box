package org.nextbox.service;


import org.nextbox.model.User;
import org.nextbox.model.Directory;
import org.nextbox.model.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by saurabh on 3/27/17.
 */
public class FilesystemAPI {
    private static boolean hasSufficientSpace(User user, File file) {
        return true;
    }

    public static boolean uploadFile(User user, File file, Path path) throws FileNotFoundException {
        if(!hasSufficientSpace(user, file)) {
            return false;
        }

        if(file.isEmpty()) {
            return false;
        }

        return upload(file, path);
    }

    public static boolean createDir(User user, Path currentDir, String newDir) throws FileNotFoundException {
        return createdir(user, currentDir, newDir);
    }

    private static boolean upload(File file, Path path) throws FileNotFoundException {
        byte b[] = file.getBytes();

        FileOutputStream fos = new FileOutputStream(path.toString() + "/" +file.getOriginalFilename());
                try {
            fos.write(b);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static java.io.File[] searchFile(String searchTerm, String path) {
        ArrayList<java.io.File> searchResults = new ArrayList<java.io.File>();
        //searchResults =
        FilesystemService.findFile(searchTerm, new java.io.File(path), searchResults);
        java.io.File[] files = new java.io.File[searchResults.size()];
        for (int i = 0; i < searchResults.size(); i++)// java.io.File file: searchResults)
            files[i] = searchResults.get(i);
        return files;
    }

    private static boolean createdir(User user, Path currentPath, String newDir) throws FileNotFoundException {
        Path newpath = Paths.get(currentPath.toString(), newDir);
        try {
            Files.createDirectory(newpath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static boolean deleteFile(String fileToDelete) throws FileNotFoundException{
        java.io.File f = new java.io.File(fileToDelete);
        try {
            f.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
