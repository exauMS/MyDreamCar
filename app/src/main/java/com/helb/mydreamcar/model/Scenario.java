package com.helb.mydreamcar.model;

public class Scenario {
    private String favoriteMake;
    private String bigStorage;
    private String numberOfPassenger;
    private String useOfCar;
    private String favoriteType;
    private String creatorEmail;
    private String abstractScenario;
    private String accurateScenario;

    public Scenario(){}

    public Scenario(String favoriteMake, String bigStorage, String numberOfPassenger, String useOfCar, String favoriteType, String creatorEmail, String abstractScenario, String accurateScenario) {
        this.favoriteMake = favoriteMake;
        this.bigStorage = bigStorage;
        this.numberOfPassenger = numberOfPassenger;
        this.useOfCar = useOfCar;
        this.favoriteType = favoriteType;
        this.creatorEmail = creatorEmail;
        this.abstractScenario = abstractScenario;
        this.accurateScenario = accurateScenario;
    }

    public String getFavoriteMake() {
        return favoriteMake;
    }

    public void setFavoriteMake(String favoriteMake) {
        this.favoriteMake = favoriteMake;
    }

    public String getBigStorage() {
        return bigStorage;
    }

    public void setBigStorage(String bigStorage) {
        this.bigStorage = bigStorage;
    }

    public String getNumberOfPassenger() {
        return numberOfPassenger;
    }

    public void setNumberOfPassenger(String numberOfPassenger) {
        this.numberOfPassenger = numberOfPassenger;
    }

    public String getUseOfCar() {
        return useOfCar;
    }

    public void setUseOfCar(String useOfCar) {
        this.useOfCar = useOfCar;
    }

    public String getFavoriteType() {
        return favoriteType;
    }

    public void setFavoriteType(String favoriteType) {
        this.favoriteType = favoriteType;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getAbstractScenario() {
        return abstractScenario;
    }

    public void setAbstractScenario(String abstractScenario) {
        this.abstractScenario = abstractScenario;
    }

    public String getAccurateScenario() {
        return accurateScenario;
    }

    public void setAccurateScenario(String accurateScenario) {
        this.accurateScenario = accurateScenario;
    }
}
