package com.example.ttt0407projectnavigationapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.List;


public class FragmentNavigation {

    public static void navigationToFragment (FragmentActivity fa, Fragment f){

        navigationBreadcrumbsToLog(fa, f);

        // get tag
        List<Fragment> lisFragments = fa.getSupportFragmentManager().getFragments();
        int n = lisFragments.size();
        int i = lisFragments.get(n - 1).getClass().toString().lastIndexOf('.');
        String strTag = lisFragments.get(n - 1).getClass().toString().substring(i + 1);

        // navigate
        FragmentTransaction ft = fa.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, f);
        //ft.replace(R.id.fragment_container, f);
        //ft.addToBackStack("dtl");
        ft.addToBackStack(strTag);
        ft.commit();
    }

    private static void navigationBreadcrumbsToLog(FragmentActivity fa, Fragment f) {

        int i;
        String str = "";

        List<Fragment> lisFragments = fa.getSupportFragmentManager().getFragments();
        int n = lisFragments.size();

        for (int ii = 0; ii < n; ii++) {
            i = lisFragments.get(ii).getClass().toString().lastIndexOf('.');
            if (ii == 0) {
                str = lisFragments.get(ii).getClass().toString().substring(i + 1);
            }
            else {
                str = str + " > " + lisFragments.get(ii).getClass().toString().substring(i + 1);
            }
        }

        // add new fragment
        i = f.getClass().toString().lastIndexOf('.');
        str = str + " > " + f.getClass().toString().substring(i + 1);

        // print to log
        Log.i("Navigation Breadcrumbs", str);
    }

/*
    // TESTING
    public static void navigationToFragment (FragmentActivity fa, Fragment f, String tag){

        FragmentTransaction ft = fa.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, f, tag);
        //ft.replace(R.id.fragment_container, f);
        ft.addToBackStack("dtl");
        ft.commit();
    }
*/
}
