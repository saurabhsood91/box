package org.nextbox.model;

import javax.activation.MimetypesFileTypeMap;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.net.URL;

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

    public Path toAbs() {
        return Paths.get(this.path).toAbsolutePath();
    }

    public File toFile() {
        return Paths.get(this.path).toAbsolutePath().toFile();
    }

    public URL toURL() throws MalformedURLException {
        return Paths.get(this.path).toAbsolutePath().toUri().toURL();
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

    public boolean pathIsPhoto() {
        absPath = Paths.get(this.path);
        f = absPath.toFile();
        // Idea from http://stackoverflow.com/questions/9643228/test-if-file-is-an-image
        String mimetype= new MimetypesFileTypeMap().getContentType(f);

        if(mimetype.startsWith("image/") || f.toString().endsWith(".png")) {
            return true;
        }
        else {
            return false;
        }
    }
}
