package com.example.assignment7;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Place> {

    ArrayList<Place> places;

    public MyAdapter(Context context, ArrayList<Place> places) {
        super(context,R.layout.list_places);
        this.places = places;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View location_view = layoutInflater.inflate(R.layout.list_places,parent,false);
        TextView location_name = location_view.findViewById(R.id.txt_place);

        location_name.setText(places.get(position).getLocation_name());


        return location_view;
    }

    @Override
    public int getCount() {
        return places.size();
    }
}
