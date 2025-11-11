package datamodels;

import java.io.Serializable;

public class User implements Serializable {

    protected  int userid;

    protected   String username;

    protected String password;

    protected String firstname;

    protected String lastname;

    protected String role;

    public User(String username, String password, String firstname, String lastname,String role) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }

    public User(int userid ,String firstname, String lastname,String role) {
        this.userid = userid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }
    public User(String firstname){
        this.firstname = firstname;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
