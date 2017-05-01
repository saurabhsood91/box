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
    public static void findFile(String name,File file, ArrayList<File> result)
    {
        //This is from http://stackoverflow.com/questions/15624226/java-search-for-files-in-a-directory
        if(name == null || name.length() == 0)
            return;
        File[] list = file.listFiles();
        if(list!=null)
            for (java.io.File fil : list)
            {
                if (fil.isDirectory())
                {
                    findFile(name,fil,result);
                }
                else if (fil.getName().toLowerCase().contains(name.toLowerCase()))
                {
                    result.add(fil);
                }
            }
    }
}
