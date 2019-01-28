package com.gmit;

import java.util.Date;
import java. sql. Timestamp;
import java.util.Objects;

public class Bug {
    private int id;
    private String name;
    private Date date;
    private long time;
    private String platform;
    private String description;
    private int status = Identifiers.STATUS_OPEN;//1 - open, 2 - assigned, 3 - closed

    public Bug(int id, int status) {
        this.id = id;
        this.status = status;
    }

    public Bug(String name, Date date, long time, String platform, String description, int status) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.platform = platform;
        this.description = description;
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Bug(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Bug() {    }
    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public long getTime() {
        return time;
    }

    public String getPlatform() {
        return platform;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bug bug = (Bug) o;
        return id == bug.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Bug{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
