package com.example.noteckv1;

public class User {
    private String userName, userPassword,email, status;
    private int Id;

    public User(String userName, String userPassword, String email, String status, int id) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.email = email;
        this.status = status;
        Id = id;
    }
    // example
    public User() {
        //enable firebase to use getter and setter
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
