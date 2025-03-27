package com.pomanager.economiccalender.Activity;

import static com.pomanager.economiccalender.Utils.CountriesFilter.getDefaultSelectedCountry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.pomanager.economiccalender.Adapter.AdapterItemList;
import com.pomanager.economiccalender.Dialog.DialogFilter;
import com.pomanager.economiccalender.Dialog.DialogListener;
import com.pomanager.economiccalender.Dialog.DialogSelectTimeZone;
import com.pomanager.economiccalender.HowTo.ActivityHowToUse;
import com.pomanager.economiccalender.Utils.Data;
import com.pomanager.economiccalender.Models.ResponseModel;
import com.pomanager.economiccalender.R;
import com.pomanager.economiccalender.Utils.DateFormatter;
import com.pomanager.economiccalender.Utils.PreferenceManager;
import com.pomanager.economiccalender.Utils.TimeFormatter;
import com.pomanager.economiccalender.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    Activity activity;
    ActivityMainBinding binding;

    Date selectedDate = new Date();

    AdapterItemList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        getDefaultSelectedCountry();

        setupDrawer();

        initViews();
        setDateText();
        setTimeZone();

        callApiData();
    }

    private void setupDrawer() {
        binding.ivMenu.setOnClickListener(v -> binding.drawerLayout.openDrawer(GravityCompat.START));

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            handleDrawerItemClick(item);
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            binding.drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleDrawerItemClick(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_timezone) {
            showTimezoneDialog();

        } else if (itemId == R.id.action_settings) {
            showFilterDialog();

        } else if (itemId == R.id.action_information) {
            startActivity(new Intent(activity, ActivityInformation.class));

        } else if (itemId == R.id.action_how_to_use) {
            startActivity(new Intent(activity, ActivityHowToUse.class)
                    .putExtra("fromSetting", true));
        }
    }

    private void showFilterDialog() {
        new DialogFilter(activity, new DialogListener() {
            @Override
            public void onDataChange() {
                callApiData();
            }
        }).show();
    }

    private void showTimezoneDialog() {
        new DialogSelectTimeZone(activity, new DialogListener() {
            @Override
            public void onDataChange() {
                callApiData();
            }
        }).show();
    }

    private void setTimeZone() {
        binding.tvSelectedTime.setText(TimeFormatter.getCurrentTimeString(activity));
        binding.tvGMTTime.setText(" (" + TimeFormatter.getCurrentGMT() + " GMT)");
    }

    private void setDateText() {
        binding.tvSelectedDate.setText(DateFormatter.formatDateForShow(selectedDate));
    }

    private void initViews() {
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callApiData();
            }
        });

        binding.btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(-1);
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(1);
            }
        });

        binding.ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });

        adapter = new AdapterItemList(activity);
        binding.rcvData.setLayoutManager(new LinearLayoutManager(activity));
        binding.rcvData.setAdapter(adapter);

        binding.btnsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });
    }

    private void setDate(int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        calendar.add(Calendar.DAY_OF_MONTH, type);
        selectedDate = DateFormatter.formatDateForApi(calendar.getTime());
        setDateText();
        callApiData();
    }

    private void callApiData() {
        binding.swipeRefresh.setRefreshing(true);

        setTimeZone();
        Data.getData(activity, DateFormatter.formatDateForApiString(selectedDate),
                PreferenceManager.getInstance(activity).getPriorityFilter(),
                PreferenceManager.getInstance(activity).getSelectedCountriesStr(),
                new Data.onDataFetchListener() {
                    @Override
                    public void onDataSuccess(ArrayList<ResponseModel> data) {
                        adapter.setData(data);
                        binding.rcvData.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_animation_fall_down));
                        binding.swipeRefresh.setRefreshing(false);

                        binding.rcvData.setVisibility(View.VISIBLE);
                        binding.llEmptyData.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(String error) {
                        adapter.setData(new ArrayList<>());
                        binding.swipeRefresh.setRefreshing(false);

                        binding.rcvData.setVisibility(View.GONE);
                        binding.llEmptyData.setVisibility(View.VISIBLE);
                    }
                });
    }
}