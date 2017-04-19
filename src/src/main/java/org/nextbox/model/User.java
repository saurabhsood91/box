package org.nextbox.model;

/**
 * Created by saurabh on 3/19/17.
 */

import org.springframework.web.bind.annotation.CrossOrigin;

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getLastname() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
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

    @Id
    @GeneratedValue
    @Column(name="id", length=10)
    private long id;

    @Column(name="username")
    String userName;

    @Column(name="password")
    String password;

    @Column(name="lastname")
    String lastName;

    @Column(name="firstname")
    String firstName;

    @Column(name="email")
    String email;

    @Column(name="role")
    String role;

    @Column(name="home_dir")
    String homeDirectory;

    public Path getHomeDirectory() {
        return Paths.get(homeDirectory);
    }

    public void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return this.getRole().compareTo("admin") == 0;
    }

}
