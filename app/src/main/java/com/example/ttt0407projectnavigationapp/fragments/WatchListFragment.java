package com.example.ttt0407projectnavigationapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ttt0407projectnavigationapp.CompanyListAdapter;
import com.example.ttt0407projectnavigationapp.MainActivity;
import com.example.ttt0407projectnavigationapp.R;
import com.example.ttt0407projectnavigationapp.model.Dao;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;

import java.util.ArrayList;
import java.util.List;


public class WatchListFragment extends Fragment {

    public WatchListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_watch_list, container, false);
        List<Company> dataList = new ArrayList<>();
        Dao dao = DaoImpl.getInstance();

        dataList = dao.getAllCompanies();

        final CompanyListAdapter adapter = new CompanyListAdapter(this.getActivity(), dataList);

        final ListView lsv = (ListView) view.findViewById(R.id.lsv_companies);
        lsv.setAdapter(adapter);


        lsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

/*
                String s = lsv.getAdapter().getItem(position).toString();
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                Log.i("holler",s);
*/

                FragmentTransaction f = getActivity().getSupportFragmentManager().beginTransaction();
                //CompanyFragment cf = new CompanyFragment();
                AddCompanyFragment cf = new AddCompanyFragment();

                f.add(R.id.fragment_container, cf);
                f.addToBackStack("dtl");
                f.commit();

                //getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        // Inflate the layout for this fragment
        return view;

/*
        ScrollView scroller = new ScrollView(getActivity());
        text = new TextView(getActivity());
        text.setText("holler");
        int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getActivity().getResources().getDisplayMetrics());
        text.setPadding(padding, padding, padding, padding);
        scroller.addView(text);
        return scroller;
*/
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

/*
        //text = new TextView(getActivity());
        text = new TextView(getActivity());
        text.setText("holleration");

        //getView().set
        getView().setBackgroundColor(Color.rgb(255, 204, 153));
*/

    }

}
