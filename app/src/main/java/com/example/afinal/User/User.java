package com.example.afinal.User;

import java.io.Serializable;

public class User implements Serializable {

    private Integer userId;
    private String fullname;
    private String username;
    private String password;
    private Integer previousScore;
    private Integer newScore;
    public User(Integer userId, String fullname, String username, String password, Integer previousScore, Integer newScore) {
        this.userId = userId;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.previousScore = previousScore;
        this.newScore = newScore;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer user) {
        this.userId = userId;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public Integer getPreviousScore() {
        return previousScore;
    }

    public void setPreviousScore(Integer previousScore) {
        this.previousScore = previousScore;
    }

    public Integer getNewSore() {
        return newScore;
    }

    public void setNewScore(Integer newScore) {
        this.newScore = newScore;
    }
}
