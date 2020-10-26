package com.verbitsky.task4.entity;

public class Student {
    private String login;
    private String name;
    private String faculty;
    private int phone;
    private Address address;

    public Student() {
    }

    public Student(String login, String name, String faculty, int phone, Address address) {
        this.login = login;
        this.name = name;
        this.faculty = faculty;
        this.phone = phone;
        this.address = address;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student: login=")
                .append(login)
                .append(", name=")
                .append(name)
                .append(", phone=")
                .append(phone)
                .append(", ")
                .append(address)
                .append(", faculty=")
                .append(faculty);
        return sb.toString();
    }
}
