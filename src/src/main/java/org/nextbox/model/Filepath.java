package org.nextbox.model;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by milroy on 4/7/17.
 */
public class Filepath {
    private String path;
    private Path absPath;
    private java.io.File f;

    public void setPath(String currentDir) {
        this.path = currentDir;
    }

    public Path getPath() {
        return Paths.get(this.path);
    }

    public Path joinPath(String fName) {
        return Paths.get(this.path, fName);
    }

    public boolean pathIsFile() {
        absPath = Paths.get(this.path);
        f = absPath.toFile();
        return f.isFile();
    }

    public boolean pathIsDir() {
        absPath = Paths.get(this.path);
        f = absPath.toFile();
        return f.isDirectory();
    }
}
