package com.example.vihu.mobileselection.Models;

public class StaffModel {
    private int staffId;
    private String staffName;
    private BranchModel branch;
    private UserModel user;

    public StaffModel(int staffId, String staffName, BranchModel branch, UserModel user) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.branch = branch;
        this.user = user;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public BranchModel getBranch() {
        return branch;
    }

    public void setBranch(BranchModel branch) {
        this.branch = branch;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
