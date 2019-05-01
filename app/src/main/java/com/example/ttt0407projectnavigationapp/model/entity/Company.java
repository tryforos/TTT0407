package com.example.ttt0407projectnavigationapp.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Company {
//public class Company implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer intId;

    private String strName;
    private String strImageUrl;

    private String strStockTicker;
    private Double dblStockPrice;

    // constructors
    public Company() {
        this("Holler, Inc","HLLR", "https://res-1.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1397191318/c3b7bb31ee854d3e692aa354d9735287.png");
    }
    public Company(String strName){
        this(strName,"HLLR", "https://holler.com/image.png");
    }
    public Company(String strName, String strStockTicker, String strImageUrl){
        super();
        this.strName = strName;
        this.strStockTicker = strStockTicker;
        this.strImageUrl = strImageUrl;
        this.dblStockPrice = 0.00;
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
    public String getStrImageUrl() {
        return strImageUrl;
    }
    public void setStrImageUrl(String strImageUrl) {
        this.strImageUrl = strImageUrl;
    }
    public String getStrStockTicker() {
        return strStockTicker;
    }
    public void setStrStockTicker(String strStockTicker) {
        this.strStockTicker = strStockTicker;
    }
    public Double getDblStockPrice() {
        return dblStockPrice;
    }
    public void setDblStockPrice(Double dblStockPrice) {
        this.dblStockPrice = dblStockPrice;
    }

    /*
    public Date getDteStockPriceDate() {
        return dteStockPriceDate;
    }
    public void setDteStockPriceDate(Date dteStockPriceDate) {
        this.dteStockPriceDate = dteStockPriceDate;
    }
    public List<Product> getLisProducts() {
        return lisProducts;
    }
    public void setLisProducts(List<Product> lisProducts) {
        this.lisProducts = lisProducts;
    }
    */

    // END getters & setters
}
