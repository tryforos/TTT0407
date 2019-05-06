package com.example.ttt0407projectnavigationapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    // constructors
    //public CompanyDragListAdapter(ArrayList<Pair<Long, String>> list, int layoutId, int grabHandleId, boolean dragOnLongPress) {
    public CompanyDragListAdapter(ArrayList<Pair<Long, Company>> list, int layoutId, int grabHandleId, boolean dragOnLongPress, Context context) {
        super();
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        this.context = context;
        setItemList(list);
    }

    // END constructors
    // NOTE: ListFragment uses "R.layout.list_item" as layoutId

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
        //String text = mItemList.get(position).second;
        Company c = mItemList.get(position).second;

        //holder.mText.setText(text);

        //Context context = getContext();
        //holder.mText.setText(text.getStrName().toString());
        new ImageDownloader(holder.imgCompanyLogo, context, c.getIntId()).execute(c.getStrImageUrl());

        //holder.txtCompanyInfo.setText(c.getStrName().toString());
        holder.txtCompanyInfo.setText(c.getStrName() +" (" + c.getStrStockTicker() + ")");

        //holder.txtStockPrice.setText(c.getStrName().toString());
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
        TextView mText;
        ImageView imgCompanyLogo;
        TextView txtCompanyInfo;
        TextView txtStockPrice;

        ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);

            mText = (TextView) itemView.findViewById(R.id.txt_company_info);
            imgCompanyLogo = (ImageView) itemView.findViewById(R.id.img_company_logo);
            txtCompanyInfo = (TextView) itemView.findViewById(R.id.txt_company_info);
            txtStockPrice = (TextView) itemView.findViewById(R.id.txt_stock_price);
        }

        @Override
        public void onItemClicked(View view) {
            Toast.makeText(view.getContext(), "CompanyDragListAdapter: Item clicked " + this.getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClicked(View view) {
            Toast.makeText(view.getContext(), "CompanyDragListAdapter: Item long clicked " + this.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
