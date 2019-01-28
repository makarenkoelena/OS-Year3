package com.gmit;

import java.util.Objects;

public class User {
    private String name;
    private int id;
    private String email;
    private String department;
    private int BugId;

    public User(String name, int id, String email, String department, int BugId) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.department = department;
        this.BugId = BugId;
    }

    public User(String name, int id, String email, String department) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.department = department;

    }
    public User(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public int getBugId() { return BugId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
