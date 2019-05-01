package com.example.ttt0407projectnavigationapp.model;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ttt0407projectnavigationapp.StockPriceDownloader;
import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@Database(entities = {Company.class, Product.class}, version = 1)
public abstract class DaoImpl extends RoomDatabase {
//public class DaoImpl implements DaoAccess {
    //like LocalDatabase in TTT0404

    public static final String DATABASE_NAME = "db";
    public abstract DaoAccess daoAccess();
    private static DaoImpl daoImpl = null;

    public IDaoObserver watchListObserver;
    public IDaoObserver companyProductsObserver;
    public IDaoObserver companyCompanyObserver;
    public IDaoObserver productObserver;

    private List<Company> lisCompanies = new ArrayList<>();
    private List<Product> lisProducts = new ArrayList<>();

    private Company selectedCompany;
    private Product selectedProduct;

    private Boolean booFirstStockDownloader = true;

    // icon URLs
    private String strBackIconUrl = "https://image.flaticon.com/icons/png/128/61/61022.png";
    private String strPlusIconUrl = "https://image.flaticon.com/icons/png/128/109/109526.png";

    ////////
    // SINGLETON
    //
    // static so that only 1 instance can be used throughout app

    public static DaoImpl getInstance(Context context) {

        if(daoImpl == null) {

            copyAttachedDatabase(context, DaoImpl.DATABASE_NAME);

            //daoImpl = Room.inMemoryDatabaseBuilder(context, DaoImpl.class).build();
            daoImpl = Room.databaseBuilder(context, DaoImpl.class, DATABASE_NAME).build();
        }

        return daoImpl;
    }
    //
    //
    ////////

    ////////
    // DATABASE COPY FROM ASSETS
    //
    // checks for existing db, copies if exists
    // will need to migrate from SQLite -> Room if applicable
    private static void copyAttachedDatabase(Context context, String databaseName) {

        // checks if db exists, copies prepopulated db if DNE
        final File dbPath = context.getDatabasePath(databaseName);

        // If the database already exists, return
        if (dbPath.exists()) {
            return;
        }

        // Make sure we have a path to the file
        dbPath.getParentFile().mkdirs();

        // Try to copy database file
        try {
            final InputStream inputStream = context.getAssets().open(databaseName);
            final OutputStream output = new FileOutputStream(dbPath);

            byte[] buffer = new byte[8192];
            int length;

            while ((length = inputStream.read(buffer, 0, 8192)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            inputStream.close();
        }
        catch (IOException e) {
            Log.d("copyAttachedDatabase", "Failed to open file", e);
            e.printStackTrace();
        }
    }
    //
    //
    ////////

    ////////
    // DATABASE PULL FUNCTIONS
    //
    public void getAllCompaniesDaoImpl() {
    //public List<Company> getAllCompaniesDaoImpl() {

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                lisCompanies = daoAccess().getAllCompanies2();
                daoImpl.setLisCompanies(lisCompanies);
            }
        });
    }

    public void getAllProductsDaoImpl (final Company company) {
    //public List<Product> getAllProductsDaoImpl (final Company company) {

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                lisProducts = daoAccess().getCompanyProducts2(company.getIntId());
                daoImpl.setLisProducts(lisProducts);
            }
        });
    }
    //
    //
    ////////

    ////////
    // DATABASE ADD / UPDATE / DELETE FUNCTIONS
    //
    // Company
    public void executeAddCompany(final Company c){

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                daoAccess().insertCompany(c);
            }
        });
    }
    public void executeDeleteCompany(final Company c){

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                daoAccess().deleteCompany(c);
            }
        });
    }
    public void executeUpdateCompany(final Company c){

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                daoAccess().updateCompany(c);
            }
        });
    }

    // Product
    public void executeAddProduct(final Product p){

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                daoAccess().insertProduct(p);
            }
        });
    }
    public void executeDeleteProduct(final Product p){

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                daoAccess().deleteProduct(p);
            }
        });
    }
    public void executeUpdateProduct(final Product p){

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                daoAccess().updateProduct(p);
            }
        });
    }
    //
    //
    ////////


    ////////
    // DATABASE UPDATE CALLS TO FRAGMENTS
    //
    public void updateCompanyList(LifecycleOwner owner) {

        this.daoAccess().getAllCompanies().observe(owner , new Observer<List<Company>>() {
            @Override
            public void onChanged(@Nullable List<Company> lis) {

                lisCompanies.clear();
                for (Company c : lis){
                    lisCompanies.add(c);
                }

                if(booFirstStockDownloader){
                    // first time thru, update stock prices, which will trigger this section again
                    booFirstStockDownloader = false;
                    new StockPriceDownloader().execute();
                }
                else if(watchListObserver != null) {
                    // update WatchListFragment every time lisCompanies changes
                    watchListObserver.update();
                }
            }
        });
    }

    public void updateProductList(LifecycleOwner owner) {

        daoImpl.daoAccess().getCompanyProducts(selectedCompany.getIntId()).observe(owner, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> lis) {

                // update products
                lisProducts.clear();
                for (Product p : lis){
                    lisProducts.add(p);
                }

                if(companyProductsObserver != null) {
                    // update Company page every time lisCompanies changes
                    companyProductsObserver.update();
                }
            }
        });
    }

    public void updateSingleCompany(LifecycleOwner owner) {

        daoImpl.daoAccess().getSingleCompany(selectedCompany.getIntId()).observe(owner, new Observer<List<Company>>() {
            @Override
            public void onChanged(@Nullable List<Company> lis) {

                selectedCompany = lis.get(0);

                if(companyCompanyObserver != null) {
                    // not needed bc data pulled from selectedCompany
                    //companyCompanyObserver.update();
                }
            }
        });
    }

    public void updateSingleProduct(LifecycleOwner owner) {

        daoImpl.daoAccess().getSingleProduct(selectedProduct.getIntId()).observe(owner, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> lis) {

                selectedProduct = lis.get(0);

                if(productObserver != null) {
                    // not needed bc data pulled from selectedProduct
                    //productObserver.update();
                }
            }
        });
    }
    //
    //
    ////////

    // getters & setters
    public String getStrBackIconUrl() {
        return strBackIconUrl;
    }
    public void setStrBackIconUrl(String strBackIconUrl) {
        this.strBackIconUrl = strBackIconUrl;
    }
    public String getStrPlusIconUrl() {
        return strPlusIconUrl;
    }
    public void setStrPlusIconUrl(String strPlusIconUrl) {
        this.strPlusIconUrl = strPlusIconUrl;
    }
    public Company getSelectedCompany() {
        return selectedCompany;
    }
    public void setSelectedCompany(Company selectedCompany) {
        this.selectedCompany = selectedCompany;
    }
    public Product getSelectedProduct() {
        return selectedProduct;
    }
    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }
    public List<Company> getLisCompanies() {
        return lisCompanies;
    }
    public void setLisCompanies(List<Company> lisCompanies) {
        this.lisCompanies = lisCompanies;
    }
    public List<Product> getLisProducts() {
        return lisProducts;
    }
    public void setLisProducts(List<Product> lisProducts) {
        this.lisProducts = lisProducts;
    }
    // END getters & setters
}

