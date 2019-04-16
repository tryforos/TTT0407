package com.example.ttt0407projectnavigationapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;


public class FragmentNavigation {

    public static void navigationToFragment (FragmentActivity fa, Fragment f){

        FragmentTransaction ft = fa.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, f);
        //ft.replace(R.id.fragment_container, f);
        ft.addToBackStack("dtl");
        ft.commit();
    }
}
