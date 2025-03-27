package com.pomanager.economiccalender.HowTo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.pomanager.economiccalender.Activity.MainActivity;
import com.pomanager.economiccalender.Models.ModelHowTo;
import com.pomanager.economiccalender.R;
import com.pomanager.economiccalender.Utils.PreferenceManager;
import com.pomanager.economiccalender.databinding.ActivityHowToUseBinding;

import java.util.ArrayList;

public class ActivityHowToUse extends AppCompatActivity {

    Activity activity;
    ActivityHowToUseBinding binding;
    private ViewPagerHowto adapter;
    private boolean fromSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHowToUseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        fromSetting = getIntent().getBooleanExtra("fromSetting", false);

        ArrayList<ModelHowTo> list = new ArrayList<>();

        setData(list);

        adapter = new ViewPagerHowto(getSupportFragmentManager(), list);
        binding.eventViewpager.setAdapter(adapter);

        binding.eventViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateButtons(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        binding.btnPrevious.setOnClickListener(v -> {
            int currentItem = binding.eventViewpager.getCurrentItem();
            if (currentItem > 0) {
                binding.eventViewpager.setCurrentItem(currentItem - 1, true);
            }
        });

        binding.btnNext.setOnClickListener(v -> {
            int currentItem = binding.eventViewpager.getCurrentItem();
            if (currentItem < adapter.getCount() - 1) {
                binding.eventViewpager.setCurrentItem(currentItem + 1, true);
            } else {
                if (fromSetting) {
                    onBackPressed();
                } else {
                    PreferenceManager.getInstance(activity).setIsFirstTime(false);
                    startActivity(new Intent(activity, MainActivity.class));
                }
            }
        });
    }

    private void setData(ArrayList<ModelHowTo> list) {
        ModelHowTo modelHowTo0 = new ModelHowTo();
        modelHowTo0.setTitle(getString(R.string.intro_title_0));
        modelHowTo0.setDescription(getString(R.string.intro_desc_0));
        modelHowTo0.setDrawable(ContextCompat.getDrawable(activity,R.drawable.how_to_1));
        list.add(modelHowTo0);

        ModelHowTo modelHowTo1 = new ModelHowTo();
        modelHowTo1.setTitle(getString(R.string.intro_title_1));
        modelHowTo1.setDescription(getString(R.string.intro_desc_1));
        modelHowTo1.setDrawable(ContextCompat.getDrawable(activity,R.drawable.how_to_2));
        list.add(modelHowTo1);

        ModelHowTo modelHowTo2 = new ModelHowTo();
        modelHowTo2.setTitle(getString(R.string.intro_title_2));
        modelHowTo2.setDescription(getString(R.string.intro_desc_2));
        modelHowTo2.setDrawable(ContextCompat.getDrawable(activity,R.drawable.how_to_3));
        list.add(modelHowTo2);
    }

    private void updateButtons(int position) {
        binding.btnPrevious.setEnabled(position > 0);
    }
}