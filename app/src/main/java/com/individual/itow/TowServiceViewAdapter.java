package com.individual.itow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TowServiceViewAdapter extends RecyclerView.Adapter<TowServiceViewHolder> {

    ListaShlep listaShlep;
    ArrayList<TowService> towServiceArrayList;

    public TowServiceViewAdapter(ListaShlep listaShlep, ArrayList<TowService> towServiceArrayList) {
        this.listaShlep = listaShlep;
        this.towServiceArrayList = towServiceArrayList;
    }

    @NonNull
    @Override
    public TowServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(listaShlep.getBaseContext());
        View view = layoutInflater.inflate(R.layout.item_sluzba, parent, false);



        return new TowServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TowServiceViewHolder holder, int position) {


        holder.mNameList.setText(towServiceArrayList.get(position).getNameTow());
        holder.mEmailList.setText(towServiceArrayList.get(position).getEmailTow());
        holder.mCityList.setText(towServiceArrayList.get(position).getCityTow());
        holder.mPhoneNumberList.setText(towServiceArrayList.get(position).getPhoneTow());
    }

    @Override
    public int getItemCount() {
        return towServiceArrayList.size();
    }
}
