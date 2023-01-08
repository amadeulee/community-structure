package io.academy.backend.academy.entity;

import java.util.Date;

public class Resident {
    private String name;
    private String pictureUrl;
    private Date lastLoginDate;

    public Resident(String name) {
        this.name = name;
    }

    public Resident(String name, String pictureUrl) {
        this.name = name;
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setLastLoginDate(Date date) {
        this.lastLoginDate = date;
    }

    public Date getLastLoginDate() {
        return this.lastLoginDate;
    }

    public boolean hasLoggedIn() {
        return this.lastLoginDate != null;
    }

    @Override
    public String toString() {
        return "Resident{" + "name='" + name + '\'' + ", pictureUrl='" + pictureUrl + '\'' + ", lastLoginDate=" + lastLoginDate + '}';
    }
}
