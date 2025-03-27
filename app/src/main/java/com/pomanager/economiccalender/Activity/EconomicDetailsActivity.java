package com.pomanager.economiccalender.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayoutMediator;
import com.pomanager.economiccalender.Adapter.ViewPagerAdapter;
import com.pomanager.economiccalender.Models.ResponseModel;
import com.pomanager.economiccalender.R;
import com.pomanager.economiccalender.Utils.TimeFormatter;
import com.pomanager.economiccalender.databinding.ActivityEconomicDetailsBinding;

public class EconomicDetailsActivity extends AppCompatActivity {

    Activity activity;
    ActivityEconomicDetailsBinding binding;
    ResponseModel model = new ResponseModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEconomicDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedDispatcher.onBackPressed();
            }
        });
        setData();

        ViewPagerAdapter adapter = new ViewPagerAdapter(this, model);
        binding.eventViewpager.setAdapter(adapter);

        // Set up TabLayout with ViewPager2 using TabLayoutMediator
        new TabLayoutMediator(binding.tabs, binding.eventViewpager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(getResources().getString(R.string.list));
                    break;
                case 1:
                    tab.setText(getResources().getString(R.string.chart));
                    break;
                case 2:
                    tab.setText(getResources().getString(R.string.description));
                    break;
            }
        }).attach();

    }

    private void setData() {
        model = (ResponseModel) getIntent().getSerializableExtra("data");

        binding.ivFlag.setImageResource(activity.getResources()
                .getIdentifier(activity.getPackageName() + ":drawable/flag_" + model.getCountry_code(), null, null));

        if (!model.getTime().isEmpty()) {
            binding.time.setVisibility(View.VISIBLE);
            binding.time.setText(TimeFormatter.convertApiTimeByTimeZone(activity, model.getTime()));
        } else {
            binding.time.setVisibility(View.GONE);
        }

        setPriorityImage(model);
        binding.title.setText(model.getEvent());

    }

    private void setPriorityImage(ResponseModel model) {
        String image = activity.getPackageName() + ":drawable/calendar_prior_1";
        switch (Integer.parseInt(model.getPriority())) {
            case 0:
            default:
                image = activity.getPackageName() + ":drawable/calendar_prior_3";
                break;

            case 1:
                image = activity.getPackageName() + ":drawable/calendar_prior_1";
                break;

            case 2:
                image = activity.getPackageName() + ":drawable/calendar_prior_2";
        }

        binding.priority.setImageResource(activity.getResources().getIdentifier(image,
                (String) null,
                (String) null));
    }
}