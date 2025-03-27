package com.pomanager.economiccalender.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pomanager.economiccalender.Adapter.AdapterFragList;
import com.pomanager.economiccalender.Models.ResponseModel;
import com.pomanager.economiccalender.databinding.FragmentDescBinding;
import com.pomanager.economiccalender.databinding.FragmentListBinding;

public class TabFragmentDescription extends Fragment {
    ResponseModel model;
    FragmentDescBinding binding;

    public TabFragmentDescription() {
    }

    public TabFragmentDescription(ResponseModel model) {
        this.model = model;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDescBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvDesc.setText(model.getEvent_description());
    }
}
