package com.example.vihu.mobileselection.Models;

public class UserModel {
    private int userId;
    private String userName;
    private String password;
    private RoleModel role;
    private boolean isActive;

    public UserModel(int userId, String userName, String password, RoleModel role, boolean isActive) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
