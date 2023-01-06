package com.viespa.models;

public class User {
    private static final User instance = new User() ;

    private int id ;
    private String account ;
    private String fullname ;
    private int role ;

    private int page = 0;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    private User(){}

    public static User getInstance(){
        return instance ;
    }

    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getAccount() {
        return account;
    }

    public int getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setAccount(String username) {
        this.account = username;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
