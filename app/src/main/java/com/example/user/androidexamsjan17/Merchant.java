package com.example.user.androidexamsjan17;

/**
 * Created by user on 27/1/2017.
 */

public class Merchant {
    private String id;
    private String legalName;
    private String category;
    private String address;
    private String imageUrl;
    private String review;

    public String getLegalName(){
        return "Το μπαλκονάκι";
    }

    public String getCategoryName(){
        return "Παραδοσιακές Ταβέρνες";
    }

    public String getAddress(){
        return "Δημ. Καβάφη";
    }

    public String getRating(){
        return "0";
    }

}
