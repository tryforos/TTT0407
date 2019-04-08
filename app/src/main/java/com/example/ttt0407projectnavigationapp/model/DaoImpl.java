package com.example.ttt0407projectnavigationapp.model;

import com.example.ttt0407projectnavigationapp.model.entity.Company;

import java.util.ArrayList;
import java.util.List;

//@Dao
public class DaoImpl implements Dao {
    //like LocalDatabase in TTT0404

    private List<Company> lisCompanies = new ArrayList<>();

    // SINGLETON
    // static so that only 1 instance can be used throughout app
    //
    private static Dao single_instance = null;
    public static Dao getInstance() {
        if (single_instance == null) {

            single_instance = new DaoImpl();
        }

        return single_instance;
    }

    // from Dao interface (i.e. "implements Dao")
    @Override
    public List<Company> getAllCompanies() {

        // DUMMY
        lisCompanies.add(new Company("Apple","https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Apple_logo_black.svg/1024px-Apple_logo_black.svg.png","APPL"));
        lisCompanies.add(new Company("Google","https://www.trainingtoyou.com/wp-content/uploads/2018/08/2000px-Google__G__Logo.svg_.png","GOOGL"));
        lisCompanies.add(new Company("Tesla","https://pngimg.com/uploads/tesla_logo/tesla_logo_PNG19.png","TSLA"));
        lisCompanies.add(new Company("Twitter","https://buzzhostingservices.com/images/twitter-logo-png-2.png","TWTR"));
        // END DUMMY

        return lisCompanies;
    }




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

