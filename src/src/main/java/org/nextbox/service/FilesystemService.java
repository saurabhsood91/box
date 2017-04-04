package org.nextbox.service;

import java.nio.file.Path;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by saurabh on 4/2/17.
 */
public class FilesystemService {
    public static File[] getDirContents(String path) {
        File currentFile = new File(path);
        return currentFile.listFiles();
    }
}
