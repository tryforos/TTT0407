package com.example.ttt0407projectnavigationapp.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Product {
//public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer intId;

    private String strName;
    private String strUrl;
    private String strShortDescription;
    private String strImageUrl;
    private Double dblPrice;
    // lngCompanyId will hold the Company id
    private Integer intCompanyId;

    // constructors
    public Product() {
        this("Hollerator #88",  "https://holler.com", "https://holler.com/image.png",0,"IDK", 88.88);
    }
    public Product(String strName) {
        this(strName, "https://holler.com", "https://holler.com/image.png",0,"IDK", 88.88);
    }
    public Product(String strName, String strUrl, String strImageUrl, Integer intCompanyId) {
        this(strName, strUrl, strImageUrl, intCompanyId, "IDK", 88.88);
    }
    public Product(String strName, String strUrl, String strImageUrl, Integer intCompanyId, String strShortDescription, Double dblPrice){
        super();
        this.strName = strName;
        this.strUrl = strUrl;
        this.strImageUrl = strImageUrl;
        this.intCompanyId = intCompanyId;
        this.strShortDescription = strShortDescription;
        this.dblPrice = dblPrice;
    }
    // END constructors

    // getters & setters
    public Integer getIntId() {
        return intId;
    }
    public void setIntId(Integer intId) {
        this.intId = intId;
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
    public Double getDblPrice() {
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
    public Integer getIntCompanyId() {
        return intCompanyId;
    }
    public void setIntCompanyId(Integer intCompanyId) {
        this.intCompanyId = intCompanyId;
    }
// END getters & setters

}
