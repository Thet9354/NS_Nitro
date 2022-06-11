package com.example.nsapplication;

public class UserModel {
    private int id;
    private String Username;
    private String email;
    private String password;
    private String fullName;
    public String NIRC;
    private String DateOfBirth;

    public UserModel(int id, String username, String email, String password, String fullName, String NIRC, String dateOfBirth) {
        this.id = id;
        this.Username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.NIRC = NIRC;
        this.DateOfBirth = dateOfBirth;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNIRC() {
        return NIRC;
    }

    public void setNIRC(String NIRC) {
        this.NIRC = NIRC;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", Username='" + Username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", NIRC='" + NIRC + '\'' +
                ", DateOfBirth='" + DateOfBirth + '\'' +
                '}';
    }
}
