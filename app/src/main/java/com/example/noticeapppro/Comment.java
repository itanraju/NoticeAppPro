package com.example.noticeapppro;

public class Comment {

    String id;
    String name;
    String comment;
    String date;
    String time;

    public Comment(String id, String name, String comment, String date, String time) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.date = date;
        this.time = time;
    }

    public  Comment(){}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
