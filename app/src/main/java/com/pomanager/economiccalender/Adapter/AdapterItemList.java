package com.pomanager.economiccalender.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pomanager.economiccalender.Activity.EconomicDetailsActivity;
import com.pomanager.economiccalender.Models.ResponseModel;
import com.pomanager.economiccalender.Utils.TimeFormatter;
import com.pomanager.economiccalender.databinding.ItemDataBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdapterItemList extends RecyclerView.Adapter<AdapterItemList.ViewHolder> {
    Context context;
    ArrayList<ResponseModel> listItems = new ArrayList<>();

    public AdapterItemList(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<ResponseModel> listItems) {
        this.listItems = listItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDataBinding binding = ItemDataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseModel model = listItems.get(position);

        if (model != null) {
            ItemDataBinding binding = holder.binding;
            binding.ivFlag.setImageResource(context.getResources()
                    .getIdentifier(context.getPackageName() + ":drawable/flag_" + model.getCountry_code(), null, null));

            binding.tvCountry.setText(model.getCountry_code().toUpperCase());

            if (!model.getTime().isEmpty()) {
                binding.timeLeft.setVisibility(View.VISIBLE);
                binding.timeLeft.setText(TimeFormatter.convertApiTimeByTimeZone(context, model.getTime()));
            } else {
                binding.timeLeft.setVisibility(View.GONE);
            }

            setPriorityImage(binding, model);
            binding.title.setText(model.getEvent());

            try {
                Date parse = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                        .parse(model.getTime());
                binding.period.setText(model.getPeriod() != null && !model.getPeriod().trim().isEmpty()
                        ? model.getPeriod()
                        : new SimpleDateFormat("yyyy").format(parse));
            } catch (Exception ignored) {
            }

            setPreForActValues(binding, model);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, EconomicDetailsActivity.class)
                        .putExtra("data", model));
            }
        });
    }

    private static void setPreForActValues(ItemDataBinding binding, ResponseModel model) {
        binding.prev.setText(model.getPrevious());
        binding.fore.setText(model.getForecast());

        if (model.getActual().trim().isEmpty()) {
            binding.timeIcon.setVisibility(View.VISIBLE);
            binding.act.setVisibility(View.GONE);

        } else {
            binding.timeIcon.setVisibility(View.GONE);
            binding.act.setVisibility(View.VISIBLE);
            binding.act.setText(model.getActual());
        }
    }

    private void setPriorityImage(ItemDataBinding binding, ResponseModel model) {
        String image = context.getPackageName() + ":drawable/calendar_prior_1";
        switch (Integer.parseInt(model.getPriority())) {
            case 0:
            default:
                image = context.getPackageName() + ":drawable/calendar_prior_3";
                break;

            case 1:
                image = context.getPackageName() + ":drawable/calendar_prior_1";
                break;

            case 2:
                image = context.getPackageName() + ":drawable/calendar_prior_2";
        }

        binding.priority.setImageResource(context.getResources().getIdentifier(image,
                (String) null,
                (String) null));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemDataBinding binding;

        public ViewHolder(ItemDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
