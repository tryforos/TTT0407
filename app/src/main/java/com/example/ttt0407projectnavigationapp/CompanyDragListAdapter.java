package com.example.ttt0407projectnavigationapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ttt0407projectnavigationapp.fragments.CompanyFragment;
import com.example.ttt0407projectnavigationapp.fragments.EditCompanyFragment;
import com.example.ttt0407projectnavigationapp.fragments.WatchListFragment;
import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.woxthebox.draglistview.DragItemAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

//public class CompanyDragListAdapter extends DragItemAdapter<Pair<Long, String>, CompanyDragListAdapter.ViewHolder> {
public class CompanyDragListAdapter extends DragItemAdapter<Pair<Long, Company>, CompanyDragListAdapter.ViewHolder> {
    // for drag-and-drop of ListView items

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;
    private Context context;

    private FragmentActivity fa;

    DaoImpl daoImpl = null;
    List<Company> lisCompanies;

    // constructors
    //public CompanyDragListAdapter(ArrayList<Pair<Long, String>> list, int layoutId, int grabHandleId, boolean dragOnLongPress) {
    public CompanyDragListAdapter(ArrayList<Pair<Long, Company>> list, int layoutId, int grabHandleId, boolean dragOnLongPress, Context context, FragmentActivity fa) {
        super();
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;

        this.context = context;
        this.fa = fa;

        daoImpl = DaoImpl.getInstance(context);
        lisCompanies = daoImpl.getLisCompanies();
        setItemList(list);
    }
    // END constructors

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    // updates info on row XML
    @Override
    public void onBindViewHolder(@NonNull CompanyDragListAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Company c = mItemList.get(position).second;

        new ImageDownloader(holder.imgCompanyLogo, context, c.getIntId()).execute(c.getStrImageUrl());

        holder.txtCompanyInfo.setText(c.getStrName() +" (" + c.getStrStockTicker() + ")");

        if (c.getDblStockPrice() > 0) {

            // format and set value
            NumberFormat format = NumberFormat.getCurrencyInstance();
            holder.txtStockPrice.setText(format.format(c.getDblStockPrice()));
        }
        else {

            // if error in receiving stock price
            holder.txtStockPrice.setText("N/A");
        }

        holder.itemView.setTag(mItemList.get(position));
    }

    @Override
    public long getUniqueItemId(int position) {
        return mItemList.get(position).first;
    }

    class ViewHolder extends DragItemAdapter.ViewHolder {

        ImageView imgCompanyLogo;
        TextView txtCompanyInfo;
        TextView txtStockPrice;

        ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);

            imgCompanyLogo = (ImageView) itemView.findViewById(R.id.img_company_logo);
            txtCompanyInfo = (TextView) itemView.findViewById(R.id.txt_company_info);
            txtStockPrice = (TextView) itemView.findViewById(R.id.txt_stock_price);
        }

        // onClick of item in ListView on WatchListFragment
        @Override
        public void onItemClicked(View view) {

            //Toast.makeText(view.getContext(), "CompanyDragListAdapter: Item clicked " + this.getAdapterPosition(), Toast.LENGTH_SHORT).show();

            lisCompanies = daoImpl.getLisCompanies();

            Company company = lisCompanies.get(this.getAdapterPosition());
            daoImpl.setSelectedCompany(company);

            WatchListFragment wlf = new WatchListFragment();
            wlf.updateStockPrices(lisCompanies);

            // create instance of next Fragment Object
            CompanyFragment fragment = new CompanyFragment();
            // navigate
            FragmentNavigation.navigationToFragment(fa, fragment);
        }

        // onLongClick of item in ListView on WatchListFragment
        @Override
        public boolean onItemLongClicked(View view) {

            //Toast.makeText(view.getContext(), "CompanyDragListAdapter: Item long clicked " + this.getAdapterPosition(), Toast.LENGTH_SHORT).show();

            Company company = lisCompanies.get(this.getAdapterPosition());
            showCompanyPopup(view, company);

            return true;
        }

        //POP UP MENU
        public void showCompanyPopup(View view, final Company company) {

            PopupMenu popup = new PopupMenu(fa, view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_company_delete_edit, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                public boolean onMenuItemClick(MenuItem item) {

                    if (item.getTitle().toString().equals("Edit")) {
                        // set company
                        daoImpl.setSelectedCompany(company);
                        // create instance of next Fragment Object
                        EditCompanyFragment fragment = new EditCompanyFragment();
                        // navigate
                        FragmentNavigation.navigationToFragment(fa, fragment);
                    }
                    else{
                        daoImpl.executeDeleteCompany(company);
                        Toast.makeText(fa,company.getStrName() + " deleted", Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(getActivity(),"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
            popup.show();
        }
    }
}
