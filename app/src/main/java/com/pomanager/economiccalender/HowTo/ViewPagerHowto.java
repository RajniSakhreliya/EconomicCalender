package com.pomanager.economiccalender.HowTo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pomanager.economiccalender.Models.ModelHowTo;

import java.util.ArrayList;

public class ViewPagerHowto extends FragmentPagerAdapter {

    private ArrayList<ModelHowTo> model = new ArrayList<>();

    public ViewPagerHowto(FragmentManager fm, ArrayList<ModelHowTo> model) {
        super(fm);
        this.model = model;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new FragmentHowToUse(model.get(1));
        } else if (position == 2) {
            return new FragmentHowToUse(model.get(2));
        }
        return new FragmentHowToUse(model.get(0));
    }

    @Override
    public int getCount() {
        return 3;
    }
}