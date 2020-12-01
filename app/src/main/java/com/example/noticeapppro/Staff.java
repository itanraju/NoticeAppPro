package com.example.noticeapppro;

public class Staff {

    String email;
    String password;
    String name;
    String staffNumber;
    String staffId;


    public Staff(String email, String password, String name, String staffNumber, String staffId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.staffNumber = staffNumber;
        this.staffId=staffId;


    }

    public Staff(){}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }


}
