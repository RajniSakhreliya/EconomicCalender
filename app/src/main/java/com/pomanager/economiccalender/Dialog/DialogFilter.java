package com.pomanager.economiccalender.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pomanager.economiccalender.Adapter.AdapterCountryList;
import com.pomanager.economiccalender.R;
import com.pomanager.economiccalender.Utils.PreferenceManager;
import com.pomanager.economiccalender.databinding.DialogFilterBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DialogFilter extends Dialog {

    Context context;
    DialogFilterBinding binding;

    String preferencePriorities = "";
    DialogListener listener;
    private AdapterCountryList adapter;
    ArrayList<String> selectedCountries = new ArrayList<>();

    public DialogFilter(@NonNull Context context, DialogListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.BOTTOM);

        preferencePriorities = PreferenceManager.getInstance(context).getPriorityFilter();
        selectedCountries = PreferenceManager.getInstance(context).getSelectedCountries();

        setPriorityInit();
        setCountryData();
        setButtons();
        setPriorities();
    }

    private void setCountryData() {
        binding.searchView.setQueryHint(context.getResources().getString(R.string.search_fruits));
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        adapter = new AdapterCountryList(context);
        adapter.setSelectedCountries(selectedCountries);

        binding.rcvCountryList.setLayoutManager(new LinearLayoutManager(context));
        binding.rcvCountryList.setAdapter(adapter);

        binding.llCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.listView.setVisibility(binding.listView.getVisibility() == View.VISIBLE
                        ? View.GONE
                        : View.VISIBLE);
            }
        });
    }

    private void setButtons() {
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.getInstance(context).setPriorityFilter(preferencePriorities);
                PreferenceManager.getInstance(context).setSelectedCountries(TextUtils.join(",", adapter.getSelectedCountries()));
                if (listener != null) listener.onDataChange();
                dismiss();
            }
        });
    }

    private void setPriorityInit() {
        binding.llPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.llPriorityFilter.getVisibility() == View.VISIBLE) {
                    binding.llPriorityFilter.setVisibility(View.GONE);
                } else {
                    binding.llPriorityFilter.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.llPriorityFilterLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPriority("1")) {
                    addPriority("1");
                } else {
                    removePriority("1");
                }
            }
        });

        binding.llPriorityFilterMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPriority("2")) {
                    addPriority("2");
                } else {
                    removePriority("2");
                }
            }
        });

        binding.llPriorityFilterHight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPriority("3")) {
                    addPriority("3");
                } else {
                    removePriority("3");
                }
            }
        });
    }

    public void addPriority(String priority) {
        String currentFilter = preferencePriorities;
        Set<String> prioritySet = new HashSet<>(Arrays.asList(currentFilter.split(",")));
        prioritySet.add(priority);
        preferencePriorities = TextUtils.join(",", prioritySet);
        setPriorities();
    }

    public void removePriority(String priority) {
        String currentFilter = preferencePriorities;
        Set<String> prioritySet = new HashSet<>(Arrays.asList(currentFilter.split(",")));
        prioritySet.remove(priority);
        preferencePriorities = TextUtils.join(",", prioritySet);
        setPriorities();
    }

    private void setPriorities() {
        binding.ivLowImage.setImageResource(checkPriority("1") ? R.drawable.calendar_prior_1 : R.drawable.calendar_prior_0);
        binding.ivMediumImage.setImageResource(checkPriority("2") ? R.drawable.calendar_prior_2 : R.drawable.calendar_prior_0);
        binding.ivHighImage.setImageResource(checkPriority("3") ? R.drawable.calendar_prior_3 : R.drawable.calendar_prior_0);

        binding.icDoneLow.setVisibility(checkPriority("1") ? View.VISIBLE : View.INVISIBLE);
        binding.icDoneMedium.setVisibility(checkPriority("2") ? View.VISIBLE : View.INVISIBLE);
        binding.icDoneHigh.setVisibility(checkPriority("3") ? View.VISIBLE : View.INVISIBLE);
    }

    private boolean checkPriority(String number) {
        return preferencePriorities.contains(number);
    }
}
