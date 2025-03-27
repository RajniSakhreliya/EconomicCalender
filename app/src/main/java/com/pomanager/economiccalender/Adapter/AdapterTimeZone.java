package com.pomanager.economiccalender.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pomanager.economiccalender.R;
import com.pomanager.economiccalender.Utils.PreferenceManager;
import com.pomanager.economiccalender.Utils.TimeFormatter;
import com.pomanager.economiccalender.databinding.ItemTimezoneBinding;

public class AdapterTimeZone extends RecyclerView.Adapter<AdapterTimeZone.ViewHolder> {
    Context context;
    String selectedTimeZone;

    public AdapterTimeZone(Context context) {
        this.context = context;
        selectedTimeZone = PreferenceManager.getInstance(context).getSelectedTimezone();
    }

    public String getSelectedTimeZone() {
        return selectedTimeZone;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTimezoneBinding binding = ItemTimezoneBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemTimezoneBinding binding = holder.binding;

        binding.tvGMTTime.setText(TimeFormatter.timeZoneTimes[position]);
        binding.tvCountrtName.setText(TimeFormatter.timeZonNames[position]);

        boolean selected = selectedTimeZone.equalsIgnoreCase(TimeFormatter.timeZonAdds[position]);

        binding.llMain.setBackgroundColor(context.getResources().getColor(selected ? R.color.black : R.color.white));
        binding.tvGMTTime.setTextColor(context.getResources().getColor(selected ? R.color.white : R.color.black));
        binding.tvCountrtName.setTextColor(context.getResources().getColor(selected ? R.color.white : R.color.black));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTimeZone = TimeFormatter.timeZonAdds[position];
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return TimeFormatter.timeZonAdds.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTimezoneBinding binding;

        public ViewHolder(ItemTimezoneBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
