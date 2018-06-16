package com.example.vihu.mobileselection.Models;

public class UserRoleModel {
    private int userRoleId;
    private UserModel user;
    private RoleModel role;

    public UserRoleModel(int userRoleId, UserModel user, RoleModel role) {
        this.userRoleId = userRoleId;
        this.user = user;
        this.role = role;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }
}
