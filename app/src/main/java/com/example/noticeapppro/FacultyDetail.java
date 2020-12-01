package com.example.noticeapppro;

public class FacultyDetail {

    String name,post,experience,skills;

    public FacultyDetail() {

    }

    public FacultyDetail(String name, String post, String experience, String skills) {
        this.name = name;
        this.post = post;
        this.experience = experience;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
