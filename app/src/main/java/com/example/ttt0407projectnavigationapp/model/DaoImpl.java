package com.example.ttt0407projectnavigationapp.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.text.BoringLayout;

import com.example.ttt0407projectnavigationapp.fragments.CompanyFragment;
import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


//@Database(entities = {Company.class, Product.class}, version = 1)
//public abstract class DaoImpl extends RoomDatabase {
public class DaoImpl implements DaoAccess {
    //like LocalDatabase in TTT0404

    ////////
    // SINGLETON
    // static so that only 1 instance can be used throughout app
    //
    private static DaoImpl daoImpl = null;
    //public abstract DaoAccess daoAccess();

/*
    public static DaoImpl getAppDatabase(Context context) {

        if(daoImpl==null) {
            daoImpl = Room.inMemoryDatabaseBuilder(context, DaoImpl.class).build();
        }
        return daoImpl;
    }
*/


    public static DaoImpl getInstance() {

        if (daoImpl == null) {
            daoImpl = new DaoImpl();
        }

        return daoImpl;
    }
    //
    //
    ////////

    private List<Company> lisCompanies = new ArrayList<>();
    private List<Product> lisProducts = new ArrayList<>();

    private Integer position;
    private Company selectedCompany;
    private Product selectedProduct;
    private Boolean booFirst = true;

    // icon URLs
    private String strBackIconUrl = "https://image.flaticon.com/icons/png/128/61/61022.png";
    private String strPlusIconUrl = "https://image.flaticon.com/icons/png/128/109/109526.png";



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
    // END getters & setters


    // from Dao interface (i.e. "implements Dao")
    @Override
    public List<Company> getAllCompanies() {

        // TODO: pull correct info
        // DUMMY

/*
        int i = 0;
        while (i < 1) {

            lisCompanies.add(new Company("Apple", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Apple_logo_black.svg/1024px-Apple_logo_black.svg.png", "APPL"));
            lisCompanies.add(new Company("Google", "https://www.trainingtoyou.com/wp-content/uploads/2018/08/2000px-Google__G__Logo.svg_.png", "GOOGL"));
            lisCompanies.add(new Company("Tesla", "https://pngimg.com/uploads/tesla_logo/tesla_logo_PNG19.png", "TSLA"));
            lisCompanies.add(new Company("Twitter", "https://buzzhostingservices.com/images/twitter-logo-png-2.png", "TWTR"));

            i++;
        }
*/
        // END DUMMY

        return lisCompanies;
    }

    @Override
    public List<Product> getAllProducts(Company company) {


        // TODO: pull correct info
/*
        // DUMMY
        lisProducts.add(new Product(
                "Hollerator #1",
                "Do stuff!",
                "https://www.journaldev.com/16813/dao-design-pattern",
                "https://buzzhostingservices.com/images/twitter-logo-png-2.png",
                123.45));
        lisProducts.add(new Product(
                "Hollerator #2",
                "Do other stuff!",
                "https://en.wikipedia.org/wiki/Battle_of_Thermopylae",
                "https://www.freelogodesign.org/Content/img/logo-ex-7.png",
                123.46));
        lisProducts.add(new Product(
                "Hollerator #3",
                "Do stuff faster!",
                "https://dayoftheshirt.com/",
                "https://dayoftheshirt.com/assets/emojis/heart_eyes-b8268d9f4d08100cde0cec9e0b372da2b21385244a3174b704c95976029f1598.png",
                123.47));
        // END DUMMY
*/

        return lisProducts;
    }

    // END getters & setters


    // database interaction
    // TODO: update to actually use database
    public void executeAddCompany(final Company c){

        lisCompanies.add(
                new Company(
                    c.getStrName(),
                    c.getStrImageUrl(),
                    c.getStrStockTicker()
                )
        );

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
    public void executeUpdateCompany(final Company c){

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

        lisProducts.add(
                new Product(
                    p.getStrName(),
                    p.getStrUrl(),
                    p.getStrImageUrl(),
                    p.getLngCompanyId(),
                    p.getStrShortDescription(),
                    p.getDblPrice()
                )
        );

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

