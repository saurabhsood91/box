package org.nextbox.service;


import org.apache.commons.io.FileUtils;
import org.nextbox.model.Filepath;
import org.nextbox.model.User;
import org.nextbox.model.File;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.validation.constraints.Null;

import org.apache.commons.io.FileUtils;
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

        Path newPath = Paths.get(path.toString(), file.getOriginalFilename());

        FileOutputStream fos = new FileOutputStream(newPath.toString());
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

    public static boolean createdir(Path newDir) throws FileNotFoundException {
        try {
            Files.createDirectory(newDir.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean deletefp(Filepath toDelete) throws FileNotFoundException {
        int choice = JOptionPane.showConfirmDialog(null, "Really delete this?", "Warning", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION)
        {
            if (toDelete.pathIsDir()) {
                // Try block taken from Oracle File class documentation: https://docs.oracle.com/javase/tutorial/essential/io/delete.html
                try {
                    Files.delete(toDelete.getPath().toAbsolutePath());
                } catch (NoSuchFileException x) {
                    System.err.format("%s: no such" + " file or directory%n", toDelete.getPath().toAbsolutePath());
                    return false;
                } catch (DirectoryNotEmptyException y) {
                    System.err.format("%s not empty%n", toDelete.getPath().toAbsolutePath());
                    int recurse = JOptionPane.showConfirmDialog(null, "Recursively delete directory?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (recurse == JOptionPane.YES_OPTION) {
                        try {
                            FileUtils.deleteDirectory(toDelete.toFile());
                        } catch (NoSuchFileException z) {
                            System.err.format("%s: no such" + " file or directory%n", toDelete.getPath().toAbsolutePath());
                            return false;
                        } catch (IOException w) {
                            // File permission problems are caught here.
                            System.err.println(w);
                            return false;
                        }
                    }
                    else {
                        return true;
                    }
                } catch (IOException x) {
                    // File permission problems are caught here.
                    System.err.println(x);
                    return false;
                }
                return true;
            }
            return true;
        }
        else
        {
            return false;
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


    public static java.io.File[] viewDir(Filepath path) throws FileNotFoundException, IOException {
        java.io.File[] directoryContents = FilesystemService.getDirContents(path.toAbs());
        return directoryContents;
    }

    public static boolean viewPhoto(Filepath path) throws FileNotFoundException, IOException {
        try {
            // This is from http://stackoverflow.com/questions/14353302/displaying-image-in-java
            BufferedImage img = ImageIO.read(path.toURL());
            ImageIcon icon = new ImageIcon(img);
            JFrame frame = new JFrame();
            frame.setLayout(new FlowLayout());
            frame.setSize(1200,800);
            JLabel lbl = new JLabel();
            lbl.setIcon(icon);
            frame.add(lbl);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static  String convertSpaceToString(long numberOfBytes)
    {
        if(numberOfBytes > 1024*1024)
            return (Double.toString(numberOfBytes/(1024.0*1024)) + " MBs");

        if(numberOfBytes > 1024)
            return (Double.toString(numberOfBytes/(1024.0)) + " KBs");

        else
            return (Long.toString(numberOfBytes) + " bytes");
    }

    public static String getUsedSpace(Path homeDirectory)
    {
        java.io.File directory = homeDirectory.toFile();
        long size = FileUtils.sizeOfDirectory(directory);
        return convertSpaceToString(size);
    }
    public static String getMaximumAvailableSpace(User user)
    {
        double gb = user.getPlan().getSpace();
        return gb + " Gbs";
    }
    public static String getFreeSpace(Path homeDirectory, User user)
    {
        long availableSpace = (long)(user.getPlan().getSpace()*1024*1024*1024);
        java.io.File directory = homeDirectory.toFile();
        long usedSpace = FileUtils.sizeOfDirectory(directory);
        long freeSpace = availableSpace - usedSpace;
        return convertSpaceToString(freeSpace);
    }

}
