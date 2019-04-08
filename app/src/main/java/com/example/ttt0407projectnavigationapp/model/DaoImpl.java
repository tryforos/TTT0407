package com.example.ttt0407projectnavigationapp.model;

import android.arch.persistence.room.Database;

import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


//@Database(entities = {Person.class, Car.class}, version = 1)
public class DaoImpl implements Dao {
    //like LocalDatabase in TTT0404

    private List<Company> lisCompanies = new ArrayList<>();
    private List<Product> lisProducts = new ArrayList<>();

    // SINGLETON
    // static so that only 1 instance can be used throughout app
    //
    private static Dao dao  = null;

    public static Dao getInstance() {

        if (dao == null) {

            dao = new DaoImpl();
        }

        return dao;
    }

    // from Dao interface (i.e. "implements Dao")
    @Override
    public List<Company> getAllCompanies() {

        // TODO: pull correct info
        // DUMMY
        lisCompanies.add(new Company("Apple","https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Apple_logo_black.svg/1024px-Apple_logo_black.svg.png","APPL"));
        lisCompanies.add(new Company("Google","https://www.trainingtoyou.com/wp-content/uploads/2018/08/2000px-Google__G__Logo.svg_.png","GOOGL"));
        lisCompanies.add(new Company("Tesla","https://pngimg.com/uploads/tesla_logo/tesla_logo_PNG19.png","TSLA"));
        lisCompanies.add(new Company("Twitter","https://buzzhostingservices.com/images/twitter-logo-png-2.png","TWTR"));
        // END DUMMY

        return lisCompanies;
    }

    @Override
    public List<Product> getAllProducts(Company company) {

        // TODO: pull correct info
        // DUMMY
        lisProducts.add(new Product(
                "Hollerator #1",
                "Do stuff!",
                "https://buzzhostingservices.com/images/twitter-logo-png-2.png",
                "https://buzzhostingservices.com/images/twitter-logo-png-2.png",
                123.45));
        lisProducts.add(new Product(
                "Hollerator #2",
                "Do other stuff!",
                "https://buzzhostingservices.com/images/twitter-logo-png-2.png",
                "https://buzzhostingservices.com/images/twitter-logo-png-2.png",
                123.46));
        lisProducts.add(new Product(
                "Hollerator #3",
                "Do stuff faster!",
                "https://buzzhostingservices.com/images/twitter-logo-png-2.png",
                "https://buzzhostingservices.com/images/twitter-logo-png-2.png",
                123.47));
        // END DUMMY

        return lisProducts;
    }



    // database interaction
    // TODO: update to actually use database
    public void executeAddCompany(final Company c){

/*
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao().insertCompany(c);
            }
        });
*/
    }
    public void executeDeleteCompany(final Company c){

/*
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao().deleteCompany(c);
            }
        });
*/
    }
    public void executeUpdateComapny(final Company c){

/*
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao().updateCompany(c);
            }
        });
*/
    }
    ////////
    public void executeAddProduct(final Product p){

/*
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao().insertCompany(c);
            }
        });
*/
    }
    public void executeDeleteProduct(final Product p){

/*
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao().deleteCompany(c);
            }
        });
*/
    }

    public void executeUpdateProduct(final Product p){

/*
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao().updateCompany(c);
            }
        });
*/
    }


}

