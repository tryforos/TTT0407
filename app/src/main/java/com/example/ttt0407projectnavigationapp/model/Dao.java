package com.example.ttt0407projectnavigationapp.model;

import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;

import java.util.List;


public interface Dao {
    //like DaoAccess in TTT0404

    List<Company> getAllCompanies();
    List<Product> getAllProducts(Company company);
//    Books getBookByIsbn(int isbn);
//    void saveBook(Books book);
//    void deleteBook(Books book);

}
