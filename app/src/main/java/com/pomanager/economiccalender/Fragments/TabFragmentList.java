package com.pomanager.economiccalender.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pomanager.economiccalender.Adapter.AdapterFragList;
import com.pomanager.economiccalender.Models.ResponseModel;
import com.pomanager.economiccalender.databinding.FragmentListBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TabFragmentList extends Fragment {
    ResponseModel model;
    FragmentListBinding binding;

    public TabFragmentList() {
    }

    public TabFragmentList(ResponseModel model) {
        this.model = model;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(model.getTime());

            binding.tvReleaseDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(parse));

            binding.tvPeriod.setText(model.getPeriod() != null && !model.getPeriod().trim().isEmpty()
                    ? model.getPeriod()
                    : new SimpleDateFormat("yyyy").format(parse));
        } catch (Exception ignored) {
        }

        //set Values
        try {
            binding.prev.setText(model.getPrevious());
        } catch (Exception e) {
            binding.prev.setText("-");
        }

        try {
            binding.fore.setText(model.getForecast());
        } catch (Exception e) {
            binding.fore.setText("-");
        }

        if (model.getActual().trim().isEmpty()) {
            binding.timeIcon.setVisibility(View.VISIBLE);
            binding.act.setVisibility(View.GONE);

        } else {
            binding.timeIcon.setVisibility(View.GONE);
            binding.act.setVisibility(View.VISIBLE);
            try {
                binding.act.setText(model.getActual());
            } catch (Exception e) {
                binding.act.setText("-");
            }
        }

        AdapterFragList adapter = new AdapterFragList(getContext());
        binding.rcvFragList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvFragList.setAdapter(adapter);

        adapter.setData(model.getEvents());
    }
}
