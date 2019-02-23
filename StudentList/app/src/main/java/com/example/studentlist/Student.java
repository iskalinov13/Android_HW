package com.example.studentlist;

public class Student {

    private String name = "";
    private String gpa = "";

    public Student(String name, String gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public String getGpa() {
        return gpa;
    }
}
