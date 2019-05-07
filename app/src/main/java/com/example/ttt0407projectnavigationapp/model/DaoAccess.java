package com.example.ttt0407projectnavigationapp.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;

import java.util.List;

@Dao
public interface DaoAccess {
    //like DaoAccess in TTT0404

    //List<Company> getAllCompanies();
    //List<Product> getAllProducts(Company company);

//    Books getBookByIsbn(int isbn);
//    void saveBook(Books book);
//    void deleteBook(Books book);

    @Insert
    void insertCompany(Company company);

    @Update
    void updateCompany(Company company);

    @Update
    void updateCompanies(Company... companies);

    @Delete
    void deleteCompany(Company company);

    @Query("SELECT * FROM Company")
    LiveData<List<Company>> getAllCompanies();

    @Query("SELECT * FROM Company ORDER BY intPosition ASC")
    LiveData<List<Company>> getAllCompaniesOrdered();

    @Query("SELECT * FROM Company WHERE intId = :intId")
    LiveData<List<Company>> getSingleCompany(Integer intId);

    @Query("SELECT * FROM Company")
    List<Company> getAllCompanies2();

    @Query("SELECT * FROM Company ORDER BY intPosition ASC")
    List<Company> getAllCompaniesOrdered2();

    ////////
    ////////
    ////////

    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Update
    void updateProducts(Product... products);

    @Delete
    void deleteProduct(Product product);

    @Query("SELECT * FROM Product WHERE intId = :intId")
    LiveData<List<Product>> getSingleProduct(Integer intId);

    @Query("SELECT * FROM Product WHERE intCompanyId IS :intCompanyId")
    LiveData<List<Product>> getCompanyProducts(Integer intCompanyId);

    @Query("SELECT * FROM Product WHERE intCompanyId IS :intCompanyId ORDER BY intPostion ASC")
    LiveData<List<Product>> getCompanyProductsOrdered(Integer intCompanyId);

    @Query("SELECT * FROM Product WHERE intCompanyId IS :intCompanyId")
    List<Product> getCompanyProducts2(Integer intCompanyId);

    @Query("SELECT * FROM Product WHERE intCompanyId IS :intCompanyId ORDER BY intPostion ASC")
    List<Product> getCompanyProductsOrdered2(Integer intCompanyId);

}
