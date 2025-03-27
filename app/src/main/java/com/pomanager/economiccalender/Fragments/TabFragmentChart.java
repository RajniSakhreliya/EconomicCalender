package com.pomanager.economiccalender.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.pomanager.economiccalender.Adapter.AdapterFragList;
import com.pomanager.economiccalender.Models.ResponseModel;
import com.pomanager.economiccalender.MyApplication;
import com.pomanager.economiccalender.R;
import com.pomanager.economiccalender.databinding.FragmentChartBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabFragmentChart extends Fragment {
    FragmentChartBinding binding;
    ResponseModel model = new ResponseModel();

    public TabFragmentChart() {
    }

    public TabFragmentChart(ResponseModel model) {
        this.model = model;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LineChart lineChart = binding.lineChart;
        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getAxisRight().setTextColor(Color.WHITE);
        lineChart.getLegend().setTextColor(Color.WHITE);

        List<ResponseModel.Events> dataList = (model.getEvents());

        Collections.reverse(dataList);

        ArrayList<Entry> entries = new ArrayList<>();

        ArrayList<String> xValues = new ArrayList<>();
        float minValue = Float.MAX_VALUE;
        float maxValue = Float.MIN_VALUE;

        // Iterate through the data and prepare the chart entries
        for (int i = 0; i < dataList.size(); i++) {
            ResponseModel.Events data = dataList.get(i);

            // Parse the actual value
            float actualValue = parseValue(data.getActual_value());

            // Find min and max values for Y-axis
            minValue = Math.min(minValue, actualValue);
            maxValue = Math.max(maxValue, actualValue);

            // Add entry to the chart
            entries.add(new Entry(i, actualValue));

            // Add the time value to X-axis labels
            xValues.add(data.getTime_value().substring(0, 4));
        }

        // Create a dataset and set properties
        LineDataSet lineDataSet = new LineDataSet(entries, getResources().getString(R.string.years));
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.tab_indicator_color));
        lineDataSet.setLineWidth(2f);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setDrawCircles(false);

        // Create LineData object
        LineData lineData = new LineData(lineDataSet);

        // Set the data and customize the chart
        lineChart.setData(lineData);
        lineChart.invalidate();

        // Customize the X-axis (timeline)
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // Customize the Y-axis (actual value)
        YAxis leftYAxis = lineChart.getAxisLeft();
        leftYAxis.setAxisMinimum(minValue - 1);
        leftYAxis.setAxisMaximum(maxValue + 1);

        // Disable the right Y-axis (optional)
        lineChart.getAxisRight().setEnabled(false);
    }

    private float parseValue(String value) {
        if (value != null && !value.isEmpty()) {
            return Float.parseFloat(value.replace("%", ""));
        }
        return 0f;
    }

}
