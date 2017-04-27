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

import javax.persistence.*;


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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPassword(String password) { this.password = password;}

    public void setPlan(Plan plan) {this.plan = plan;}

    public String getLastname() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Plan getPlan() { return plan;}

    public long getId() {
        return id;
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

//    @Column(name="plan")
//    String plan;
    @ManyToOne
    @JoinColumn(name="plan_id",referencedColumnName="id")
    Plan plan;



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


    @Column(name="activation_status")
    String activation_status;

    public boolean isActive() {
        return this.getactivation_status().compareTo("active") == 0;
    }

}
