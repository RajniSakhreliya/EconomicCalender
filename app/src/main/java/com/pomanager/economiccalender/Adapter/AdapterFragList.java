package com.pomanager.economiccalender.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pomanager.economiccalender.Models.ResponseModel;
import com.pomanager.economiccalender.R;
import com.pomanager.economiccalender.databinding.ItemFragmentListBinding;

import java.util.ArrayList;
import java.util.List;

public class AdapterFragList extends RecyclerView.Adapter<AdapterFragList.ViewHolder> {

    Context context;
    List<ResponseModel.Events> eventsArrayList = new ArrayList<>();

    public AdapterFragList(Context context) {
        this.context = context;
    }

    public void setData(List<ResponseModel.Events> eventsArrayList) {
        this.eventsArrayList = eventsArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFragmentListBinding binding = ItemFragmentListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemFragmentListBinding binding = holder.binding;
        ResponseModel.Events model = eventsArrayList.get(position);

        binding.llMain.setBackgroundColor(ContextCompat.getColor(context, (position % 2) == 0 ? R.color.colorPrimary : R.color.colorPrimaryDark));

        binding.tvDate.setText(model.getTime_value());

        try {
            binding.tvFor.setText(model.getForecast_value().trim().isEmpty() ? "-" : model.getForecast_value());
        } catch (Exception e) {
            binding.tvFor.setText("-");
        }

        try {
            binding.tvAct.setText(model.getActual_value().trim().isEmpty() ? "-" : model.getActual_value());
        } catch (Exception e) {
            binding.tvAct.setText("-");
        }
    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemFragmentListBinding binding;

        public ViewHolder(ItemFragmentListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
