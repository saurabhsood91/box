package org.nextbox.model;

/**
 * Created by saurabh on 3/19/17.
 */

// Hibernate code referred:s
// http://www.roseindia.net/spring/spring4/login-form-using-spring-mvc-and-hibernate.shtml

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.io.Serializable;
import java.sql.Timestamp;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Embeddable;


@Entity
@Table(name = "account")
public class User implements Serializable {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getactivation_status() {
        return activation_status;
    }

    public void setactivation_status(String activation_status) {
        this.activation_status = activation_status;
    }

    @Id
    @GeneratedValue
    @Column(name="id", length=10)
    private long id;

    @Column(name="username")
    String userName;

    @Column(name="password")
    String password;

    public Path getHomeDirectory() {
        return Paths.get(homeDirectory);
    }

    public void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }

    @Column(name="home_dir")
    String homeDirectory;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(name="role")
    String role;

    public boolean isAdmin() {
        return this.getRole().compareTo("admin") == 0;
    }


    @Column(name="activation_status")
    String activation_status;

    public boolean isActive() {
        return this.getactivation_status().compareTo("active") == 0;
    }

    public boolean shareFile(String fileToShare, User userToShare) throws IOException {
        // get the user's home directory
        Path homeDirectory = userToShare.getHomeDirectory();

        // Convert the file to a Path object
        Path filePath = Paths.get(fileToShare);

        // New Path
        Path outputPath = Paths.get(homeDirectory.toString(), filePath.getFileName().toString());

        Files.copy(filePath, outputPath);
        return true;
    }

}
