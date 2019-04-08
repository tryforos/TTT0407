package com.example.ttt0407projectnavigationapp.model;

import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;

import java.util.List;


//@Dao
public interface Dao {
    //like DaoAccess in TTT0404

    List<Company> getAllCompanies();
    List<Product> getAllProducts(Company company);

//    Books getBookByIsbn(int isbn);
//    void saveBook(Books book);
//    void deleteBook(Books book);


        /*
    @Insert
    void insertPerson(Person person);

    @Update
    void updatePerson(Person person);

    @Delete
    void deletePerson(Person person);

    @Query("SELECT * FROM Person")
    LiveData<List<Person>> fetchAllPersons();

    @Query("SELECT * FROM Person")
    List<Person> fetchAllPersons2();

    @Query("SELECT * FROM Person WHERE fullname IS :fullname LIMIT 1")
    List<Person> getPersonByName(String fullname);

*//*
    @Query("SELECT count(*) FROM Person")
    Long fetchThisThing();
*//*

    @Insert
    void insertCar(Car car);

    @Update
    void updateCar(Car car);

    @Delete
    void deleteCar(Car car);

    @Query("SELECT * FROM Car")
    LiveData<List<Car>> fetchAllCars();

    @Query("SELECT * FROM Car WHERE ownerId IS :ownerId")
    LiveData<List<Car>> getCarsForOwner(String ownerId);*/


}
