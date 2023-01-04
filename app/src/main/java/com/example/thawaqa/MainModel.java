package com.example.thawaqa;

public class MainModel  {

    String Auth , Price , image , name;

    MainModel(){



    }

    public MainModel(String auth, String price, String image, String name) {
        this.Auth = auth;
        this.Price = price;
        this.image = image;
        this.name = name;
    }



    public String getAuth() {
        return Auth;
    }

    public String getPrice() {
        return Price;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }



}
