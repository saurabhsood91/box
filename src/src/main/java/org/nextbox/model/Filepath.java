package org.nextbox.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

/**
 * Created by milroy on 4/7/17.
 */
public class Filepath {
    private String path;
    private Path absPath;
    private File f;

    public void setPath(String currentDir) {
        this.path = currentDir;
    }

    public Path getPath() {
        return Paths.get(this.path);
    }

    public Path joinPath(String fName) {

        return Paths.get(this.path, fName);
    }

    public Path toAbs() {
        return Paths.get(this.path).toAbsolutePath();
    }

    public File fptoFile() {
        return Paths.get(this.path).toFile();
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
