package com.example.ttt0407projectnavigationapp;

public interface IStockPriceFetcher {

    // allows for StockPriceDownloader to call this follow up function on any fragment
    void stockPriceFetched(Double dbl);
}
