package com.individual.itow;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TowServiceViewHolder extends RecyclerView.ViewHolder {

    public TextView mNameList, mEmailList, mCityList, mPhoneNumberList;

    public TowServiceViewHolder(@NonNull View itemView) {
        super(itemView);

        mNameList = itemView.findViewById(R.id.nameList);
        mEmailList = itemView.findViewById(R.id.emailList);
        mCityList = itemView.findViewById(R.id.cityList);
        mPhoneNumberList = itemView.findViewById(R.id.phoneNumberList);

    }
}
