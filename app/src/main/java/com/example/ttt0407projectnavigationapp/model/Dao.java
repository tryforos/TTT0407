package com.example.ttt0407projectnavigationapp.model;

import com.example.ttt0407projectnavigationapp.model.entity.Company;
import java.util.List;


public interface Dao {
    //like DaoAccess in TTT0404

    List<Company> getAllCompanies();
//    Books getBookByIsbn(int isbn);
//    void saveBook(Books book);
//    void deleteBook(Books book);

}