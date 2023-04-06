package com.helb.mydreamcar.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Car {

    @JsonProperty("id")
    public int id;
    @JsonProperty("year")
    public int year;
    @JsonProperty("make")
    public String make;
    @JsonProperty("model")
    public String model;
    @JsonProperty("type")
    public String type;

    public String imageUrl;

    public Car(){};

    public Car(int id, int year, String make, String model, String type) {
        this.id = id;
        this.year = year;
        this.make = make;
        this.model = model;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
