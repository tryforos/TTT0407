package com.example.ttt0407projectnavigationapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.model.Dao;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;

public class CompanyFragment extends Fragment {

    public CompanyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Dao dao = DaoImpl.getInstance();
        dao.getAllCompanies();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company, container, false);
    }

}
