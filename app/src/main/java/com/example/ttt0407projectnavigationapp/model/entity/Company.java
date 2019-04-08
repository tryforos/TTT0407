package com.example.ttt0407projectnavigationapp.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Entity
public class Company {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String strName;
    private String strImageUrl;

    private String strStockTicker;
    private Double dblStockPrice;
    private Date datStockPriceDate;

    private List<Product> lisProducts = new ArrayList<>();


    // constructors
    public Company() {
        this("Holler, Inc", "https://holler.com/image.png","HLLR");
    }
    public Company(String strName){
        this(strName, "https://holler.com/image.png","HLLR");
    }
    public Company(String strName, String strImageUrl, String strStockTicker){
        super();
        this.strName = strName;
        this.strImageUrl=strImageUrl;
        this.strStockTicker=strStockTicker;
        //TODO: populate elsewhere
        this.dblStockPrice = 123.45;
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
    public double getDblStockPrice() {
        return dblStockPrice;
    }
    public void setDblStockPrice(double dblStockPrice) {
        this.dblStockPrice = dblStockPrice;
    }
    public Date getDatStockPriceDate() {
        return datStockPriceDate;
    }
    public void setDatStockPriceDate(Date datStockPriceDate) {
        this.datStockPriceDate = datStockPriceDate;
    }
    public List<Product> getLisProducts() {
        return lisProducts;
    }
    public void setLisProducts(List<Product> lisProducts) {
        this.lisProducts = lisProducts;
    }
    // END getters & setters
}
