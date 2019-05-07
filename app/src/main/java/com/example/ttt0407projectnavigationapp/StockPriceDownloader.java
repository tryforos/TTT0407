package com.example.ttt0407projectnavigationapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.example.ttt0407projectnavigationapp.fragments.AddCompanyFragment;
import com.example.ttt0407projectnavigationapp.fragments.EditCompanyFragment;
import com.example.ttt0407projectnavigationapp.fragments.WatchListFragment;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class StockPriceDownloader extends AsyncTask<String, Void, JSONObject> {

    // received from alphavantage.co
    String strApiKey = "1Y202SZ6USUHQL35";
    String strApiUrl;
    //private final String RemoteUrl = "https://turntotech.firebaseio.com/digitalleash/users/%s/%s.json";

    DaoImpl daoImpl;
    List<Company> lisCompanies = new ArrayList<>();
    Context ctx;
    Object objUiReference;
    Fragment fraUiReference;

    JSONObject jsnObject;
    Boolean booTickersGood;
    String strBadTicker;

    IStockPriceFetcher iStockPriceFetcher = null;

    // constructors
    // checks only 1 ticker
    public StockPriceDownloader(IStockPriceFetcher iStockPriceFetcher, String ticker) {
        super();
        this.iStockPriceFetcher = iStockPriceFetcher;
        this.lisCompanies = new ArrayList<>();
        Company c =  new Company();
        c.setStrStockTicker(ticker);
        this.lisCompanies.add(c);
    }

    // checks all tickers
    public StockPriceDownloader() {
        super();
        this.daoImpl = DaoImpl.getInstance(null);
        this.lisCompanies = daoImpl.getLisCompanies();
    }
    // END constructors


    private String updateStrApiUrl(){

        String str;
        str = "";
        for (int i = 0; i < lisCompanies.size(); i++) {
            if (i == 0){
                str = (lisCompanies.get(i).getStrStockTicker());
            }
            else {
                str = str + "," + (lisCompanies.get(i).getStrStockTicker());
            }
        }
        strApiUrl = "https://www.alphavantage.co/query?function=BATCH_STOCK_QUOTES&symbols=" + str + "&apikey=" + strApiKey;

        return strApiUrl;
    }

    private JSONObject downloadData(String strUrlString) {

        final String TAG = "hollerrr:";

        try {
            URL url = new URL(strUrlString);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

            // Create the urlConnection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");

            //check this after connecting to make sure it worked
            int intStatusCode = urlConnection.getResponseCode();

            //get input stream
            InputStream is = urlConnection.getInputStream();

            //convert to JSON
            String s = InputStreamHandling.inputStreamToString(is);
            JSONObject jsonObject = new JSONObject(s);

            //log
            Log.d(TAG, jsonObject.toString());
            Log.d(TAG +" d:", "what what: chicken butt");

            return jsonObject;

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Double getStockPrice(JSONObject jsoOriginal, String strKey1, String strKey2, String strKey2Value, String strKey3){

        try {

            JSONArray jsa = jsoOriginal.getJSONArray(strKey1);

            for(int i = 0; i < jsa.length(); i++) {

                JSONObject jso = jsa.getJSONObject(i);

                Log.d(TAG, "json (" + i + ") = " + jso.toString());

                if (jso.getString(strKey2).toString().equals(strKey2Value)){
                    Double dbl = Double.valueOf(jso.getString(strKey3));
                    return dbl;
                }
            }

            // if we do not find our ticker
            return 0.0;
        }
        catch (Exception e) {

            e.printStackTrace();
            return -1.0;
        }
    }

    ////////
    // AsycnTask Overrides
    //
    // variable type returned needs to match variable in class
    @Override
    protected JSONObject doInBackground(String... strings) {

        String str;
        JSONObject jsoOriginal;

        // build correct URL for API pull
        str = updateStrApiUrl();

        // get stock data, place in JSON Object
        jsoOriginal = downloadData(str);

        return jsoOriginal;
    }

    @Override
    protected void onPostExecute(JSONObject jso) {

        // wait until data is retrieved before setting variables
        super.onPostExecute(jso);

        if(iStockPriceFetcher != null) {

            // single stock
            Double dbl = getStockPrice(jso, "Stock Quotes", "1. symbol", lisCompanies.get(0).getStrStockTicker(), "2. price");
            iStockPriceFetcher.stockPriceFetched(dbl);
        }
        else {

            // pull all stock prices from JSON, update companies
            for (int i = 0; i < lisCompanies.size(); i++) {

                Double dbl = getStockPrice(jso, "Stock Quotes", "1. symbol", lisCompanies.get(i).getStrStockTicker(), "2. price");
                lisCompanies.get(i).setDblStockPrice(dbl);
            }

            daoImpl.executeUpdateCompanies(lisCompanies);
        }
    }
    //
    //
    ////////

    // getters & setters
    public String getStrApiUrl() {
        return strApiUrl;
    }
    public void setStrApiUrl(String strApiUrl) {
        this.strApiUrl = strApiUrl;
    }
    public Boolean getBooTickersGood() {
        return booTickersGood;
    }
    public void setBooTickersGood(Boolean booTickersGood) {
        this.booTickersGood = booTickersGood;
    }
    public String getStrBadTicker() {
        return strBadTicker;
    }
    public void setStrBadTicker(String strBadTicker) {
        this.strBadTicker = strBadTicker;
    }
    // END getters & setters
}
