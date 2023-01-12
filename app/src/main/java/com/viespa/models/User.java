package com.viespa.models;

public class User {
    private static final User instance = new User();

    private int id;
    private String account;
    private String fullname;
    private int role;

    private int page = 0;

    private User() {
    }

    public static User getInstance() {
        return instance;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String username) {
        this.account = username;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
