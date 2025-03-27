package com.pomanager.economiccalender.HowTo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pomanager.economiccalender.Models.ModelHowTo;
import com.pomanager.economiccalender.Models.ResponseModel;
import com.pomanager.economiccalender.databinding.FragmentChartBinding;
import com.pomanager.economiccalender.databinding.FragmentHowToBinding;

public class FragmentHowToUse extends Fragment {
    FragmentHowToBinding binding;
    ModelHowTo model = new ModelHowTo();

    public FragmentHowToUse() {
    }

    public FragmentHowToUse(ModelHowTo model) {
        this.model = model;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHowToBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvTitle.setText(model.getTitle());
        binding.ivImage.setImageDrawable(model.getDrawable());
        binding.tvDesc.setText(model.getDescription());
    }

}
