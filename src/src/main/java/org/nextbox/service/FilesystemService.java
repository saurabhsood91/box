package org.nextbox.service;

import java.nio.file.Path;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by saurabh on 4/2/17.
 */
public class FilesystemService {
    public static File[] getDirContents(Path path) {
        File currentFile = path.toFile();
        return currentFile.listFiles();
    }
}
