package org.nextbox.service;


import org.nextbox.model.Filepath;
import org.nextbox.model.User;
import org.nextbox.model.Directory;
import org.nextbox.model.File;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

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

    public static boolean createDir(User user, Filepath newDir) throws FileNotFoundException {
        return createdir(Paths.get(user.getHomeDirectory().toAbsolutePath().toString(),
                newDir.getPath().toString()));
    }

    public static boolean deleteFP(User user, Filepath delDir) throws FileNotFoundException {
        return deletefp(delDir);
    }

    public static boolean moveRN(User user, Filepath source) throws IOException {
        return movern(user.getHomeDirectory(), source);
    }

    private static boolean upload(File file, Path path) throws FileNotFoundException {
        byte b[] = file.getBytes();

        FileOutputStream fos = new FileOutputStream(path.toString() + file.getOriginalFilename());
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

    private static boolean createdir(Path newDir) throws FileNotFoundException {
        try {
            Files.createDirectory(newDir.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean deletefp(Filepath toDelete) throws FileNotFoundException {
        int choice = JOptionPane.showConfirmDialog(null, "Should i delete this?", "Warning", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION)
        {
            // Try block taken from Oracle File class documentation: https://docs.oracle.com/javase/tutorial/essential/io/delete.html
            try {
                Files.delete(toDelete.getPath().toAbsolutePath());
            } catch (NoSuchFileException x) {
                System.err.format("%s: no such" + " file or directory%n", toDelete.getPath().toAbsolutePath());
                return false;
            } catch (DirectoryNotEmptyException x) {
                System.err.format("%s not empty%n", toDelete.getPath().toAbsolutePath());
                return false;
            } catch (IOException x) {
                // File permission problems are caught here.
                System.err.println(x);
                return false;
            }
            return true;
        }
        else
        {
            return true;
        }
    }

    private static boolean movern(Path homeDir, Filepath source) throws IOException {
        String dest = JOptionPane.showInputDialog("Type new name or destination, relative to ~/");
        Path relDest = Paths.get(homeDir.toAbsolutePath().toString(), dest);

        // Try block taken from Oracle File class documentation: https://docs.oracle.com/javase/tutorial/essential/io/delete.html
        try {
            Files.move(source.getPath(), relDest);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", source.getPath().toAbsolutePath());
            return false;
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", source.getPath().toAbsolutePath());
            return false;
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
            return false;
        }
        return true;
    }

    public static boolean download(User user, String path, HttpServletResponse response)
    {
        Path file = Paths.get(path);

        if(file.toFile().isDirectory())
            return false;

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.addHeader("Content-Disposition", "attachment; filename="+path);
        try
        {
            Files.copy(file, response.getOutputStream());
            response.getOutputStream().flush();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return  true;
    }

}
