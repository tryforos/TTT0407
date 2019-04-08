package com.example.ttt0407projectnavigationapp.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String strName;
    private String strUrl;
    private String strShortDescription;
    private String strImageUrl;
    private Double dblPrice;

    // constructors
    public Product() {
        this("Hollerator #88", "IDK", "https://holler.com", "https://holler.com/image.png", 88.88);
    }
    public Product(String strName) {
        this(strName, "IDK","https://holler.com", "https://holler.com/image.png", 88.88);
    }
    public Product(String strName, String strShortDescription, String strUrl, String strImageUrl, Double dblPrice){
        super();
        this.strName = strName;
        this.strShortDescription = strShortDescription;
        this.strUrl = strUrl;
        this.strImageUrl = strImageUrl;
        this.dblPrice = dblPrice;
    }
    // END constructors

    // getters & setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStrName() {
        return strName;
    }
    public void setStrName(String strName) {
        this.strName = strName;
    }
    public String getStrUrl() {
        return strUrl;
    }
    public void setStrUrl(String strUrl) {
        this.strUrl = strUrl;
    }
    public String getStrImageUrl() {
        return strImageUrl;
    }
    public void setStrImageUrl(String strImageUrl) {
        this.strImageUrl = strImageUrl;
    }
    public double getDblPrice() {
        return dblPrice;
    }
    public void setDblPrice(Double dblPrice) {
        this.dblPrice = dblPrice;
    }
    public String getStrShortDescription() {
        return strShortDescription;
    }
    public void setStrShortDescription(String strShortDescription) {
        this.strShortDescription = strShortDescription;
    }

    // END getters & setters

}
