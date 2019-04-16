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

    List<Company> getAllCompanies();
    List<Product> getAllProducts(Company company);

//    Books getBookByIsbn(int isbn);
//    void saveBook(Books book);
//    void deleteBook(Books book);

/*
    @Insert
    void insertCompany(Company company);

    @Update
    void updateCompany(Company company);

    @Delete
    void deleteCompany(Company company);

    @Query("SELECT * FROM Company")
    LiveData<List<Company>> fetchAllCompanies();

    @Query("SELECT * FROM Company")
    List<Company> fetchAllCompanies2();

//    @Query("SELECT * FROM Person WHERE fullname IS :fullname LIMIT 1")
//    List<Person> getPersonByName(String fullname);
//
//    @Query("SELECT count(*) FROM Person")
//    Long fetchThisThing();

    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("SELECT * FROM Product WHERE lngCompanyId IS :lngCompanyId")
    LiveData<List<Product>> getProductsForCompany(String lngCompanyId);
*/

}
