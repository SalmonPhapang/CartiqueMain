package car.com.cartique.model;

import java.io.Serializable;

public class User implements Serializable{
    private String name;
    private String surName;
    private String address;
    private String number;
    private String city;
    private String email;
    private String userID;
    private String uniqueID;
    private String notificationID;
    private Car car;

    public User(){

    }
    public User(String name, String surName, String address, String number,String city) {
        this.name = name;
        this.surName = surName;
        this.address = address;
        this.number = number;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }
    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", address='" + address + '\'' +
                ", number='" + number + '\'' +
                ", email='" + email + '\'' +
                ", userID='" + userID + '\'' +
                ", uniqueID='" + uniqueID + '\'' +
                ", notificationID='" + notificationID + '\'' +
                ", car=" + car +
                '}';
    }
}
