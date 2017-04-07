package org.nextbox.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by saurabh on 3/27/17.
 */
public class File {
    public MultipartFile getFile() {
        return file;
    }

    public File(MultipartFile file) {
        this.file = file;
    }

    public File() {
        this.file = null;
    }

    public boolean isEmpty() {
        return this.file.isEmpty();
    }

    public byte[] getBytes() {
        byte[] bytes = null;
        try {
            bytes = this.file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public String getOriginalFilename() {
        return this.file.getOriginalFilename();
    }

    protected MultipartFile file;
    private String filename;
}
