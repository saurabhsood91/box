package org.nextbox.service;


import org.nextbox.model.User;
import org.nextbox.model.Directory;
import org.nextbox.model.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by saurabh on 3/27/17.
 */
public class FilesystemAPI {
    private static boolean hasSufficientSpace(User user, File file) {
        return true;
    }

    public static boolean uploadFile(User user, File file, String path) throws FileNotFoundException {
        if(!hasSufficientSpace(user, file)) {
            return false;
        }

        if(file.isEmpty()) {
            return false;
        }

        return upload(file, path);
    }

    public static boolean createDir(User user, String currentDir, String newDir) throws FileNotFoundException {
        return createdir(user, currentDir, newDir);
    }

    private static boolean upload(File file, String path) throws FileNotFoundException {
        byte b[] = file.getBytes();

        FileOutputStream fos = new FileOutputStream(path + file.getOriginalFilename());
        try {
            fos.write(b);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean createdir(User user, String currentPath, String newDir) throws FileNotFoundException {
        Path path = FileSystems.getDefault().getPath(currentPath, newDir);
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
