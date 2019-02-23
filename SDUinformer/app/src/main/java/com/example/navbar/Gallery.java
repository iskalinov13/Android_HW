package com.example.navbar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import steelkiwi.com.library.view.IndicatorView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Gallery extends Fragment {

    List<Integer> listImages = new ArrayList<>();
    public Gallery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery1, container, false);
        // Inflate the layout for this fragment
        listImages.add(R.drawable.report1main);
        listImages.add(R.drawable.report3main);
        listImages.add(R.drawable.report3main);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(listImages,this.getContext());
        ViewPager viewPager  = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);

        IndicatorView indicatorView = (IndicatorView) v.findViewById(R.id.indicatorView);
        indicatorView.attachViewPager(viewPager);



        return v;
    }




}
