package com.helb.mydreamcar.model;

public class Post {
    private String make;
    private String model;
    private String year;
    private String type;
    private String creator;
    private String date;
    private String url;
    private String location;
    private String creatorEmail;

    public Post(){}
    public Post(String make, String model, String year, String type, String creator, String date, String url, String location, String creatorEmail) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.type = type;
        this.creator = creator;
        this.date = date;
        this.url = url;
        this.location = location;
        this.creatorEmail = creatorEmail;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }
}
