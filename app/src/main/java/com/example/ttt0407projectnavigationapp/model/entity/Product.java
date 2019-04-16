package com.example.ttt0407projectnavigationapp.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

//@Entity
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String strName;
    private String strUrl;
    private String strShortDescription;
    private String strImageUrl;
    private Double dblPrice;
    // lngCompanyId will hold the Company id
    private Long lngCompanyId;

    // constructors
    public Product() {
        this("Hollerator #88",  "https://holler.com", "https://holler.com/image.png",0L,"IDK", 88.88);
    }
    public Product(String strName) {
        this(strName, "https://holler.com", "https://holler.com/image.png",0L,"IDK", 88.88);
    }
    public Product(String strName, String strUrl, String strImageUrl, Long lngCompanyId) {
        this(strName, strUrl, strImageUrl, lngCompanyId, "IDK", 88.88);
    }
    public Product(String strName, String strUrl, String strImageUrl, Long lngCompanyId, String strShortDescription, Double dblPrice){
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
    public Long getLngCompanyId() {
        return lngCompanyId;
    }
    public void setLngCompanyId(Long lngCompanyId) {
        this.lngCompanyId = lngCompanyId;
    }
// END getters & setters

}
