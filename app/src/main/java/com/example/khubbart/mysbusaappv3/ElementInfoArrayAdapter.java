package com.example.khubbart.mysbusaappv3;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khubbart.mysbusaappv3.Model.ElementInfo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ElementInfoArrayAdapter extends RecyclerView.Adapter<ElementInfoArrayAdapter.ViewHolder> {
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listElementInfoLayout;
    private ArrayList<ElementInfo> elementInfoList;
    public String tempString;
    NumberFormat numberFormat = new DecimalFormat("###.00");

    // Constructor of the class
    public ElementInfoArrayAdapter(int layoutId, ArrayList<ElementInfo> elementInfoList) {
        listElementInfoLayout = layoutId;
        this.elementInfoList = elementInfoList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return elementInfoList == null ? 0 : elementInfoList.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listElementInfoLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        TextView elementCodeTV = holder.elementCodeTV;
        TextView elementNameTV = holder.elementNameTV;
        TextView elementBaseValueTV = holder.elementBaseValueTV;
        elementCodeTV.setText(elementInfoList.get(listPosition).getElementCode());
        elementNameTV.setText(elementInfoList.get(listPosition).getElementName());
        elementBaseValueTV.setText(numberFormat.format(elementInfoList.get(listPosition).getElementBaseValue()));
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView elementCodeTV;
        public TextView elementNameTV;
        public TextView elementBaseValueTV;

        public ViewHolder(View elementInfoView) {
            super(elementInfoView);
            elementInfoView.setOnClickListener(this);
            elementCodeTV = elementInfoView.findViewById(R.id.textViewElementCode);
            elementNameTV = elementInfoView.findViewById(R.id.textViewElementName);
            elementBaseValueTV = elementInfoView.findViewById(R.id.textViewElementBaseValue);
        }

        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition());
        }
    }
}
