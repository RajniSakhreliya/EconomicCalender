package com.pomanager.economiccalender.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pomanager.economiccalender.Fragments.TabFragmentChart;
import com.pomanager.economiccalender.Fragments.TabFragmentDescription;
import com.pomanager.economiccalender.Fragments.TabFragmentList;
import com.pomanager.economiccalender.Models.ResponseModel;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private ResponseModel model = new ResponseModel();

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, ResponseModel model) {
        super(fragmentActivity);
        this.model = model;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new TabFragmentChart(model);
        } else if (position == 2) {
            return new TabFragmentDescription(model);
        }
        return new TabFragmentList(model);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}