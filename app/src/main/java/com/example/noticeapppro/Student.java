package com.example.noticeapppro;

public class Student {

    String email;
    String password;
    String name;
    String enrollmentNo;
    String studentId;

    public Student(String name, String enrollmentNo) {
        this.name = name;
        this.enrollmentNo = enrollmentNo;
    }

    public Student(String email, String password, String name, String enrollmentNo, String studentId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.enrollmentNo = enrollmentNo;
        this.studentId = studentId;
    }

    public Student(){}

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnrollmentNo() {
        return enrollmentNo;
    }

    public void setEnrollmentNo(String enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }
}
