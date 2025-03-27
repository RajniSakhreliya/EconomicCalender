package com.pomanager.economiccalender.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pomanager.economiccalender.Utils.CountriesFilter;
import com.pomanager.economiccalender.databinding.ItemCountryBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdapterCountryList extends RecyclerView.Adapter<AdapterCountryList.ViewHolder> {
    Context context;
    ArrayList<String> allCountries = new ArrayList<>();
    ArrayList<String> selectedCountries = new ArrayList<>();

    public AdapterCountryList(Context context) {
        this.context = context;
        allCountries = CountriesFilter.allCountries;
    }

    public void setSelectedCountries(ArrayList<String> selectedCountries) {
        this.selectedCountries = selectedCountries;
        notifyDataSetChanged();
    }

    public ArrayList<String> getSelectedCountries() {
        return selectedCountries;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCountryBinding binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemCountryBinding binding = holder.binding;
        String countryCode = allCountries.get(position);

        binding.ivCountryFlag.setImageResource(context.getResources()
                .getIdentifier(context.getPackageName() + ":drawable/flag_" + countryCode, null, null));

        int flagResourceId = context.getResources().getIdentifier("country_" + countryCode,
                "string", context.getPackageName());

        binding.tvCountryName.setText(flagResourceId);

        binding.ivSelected.setVisibility(CountriesFilter.containsCountry(selectedCountries, allCountries.get(position))
                ? View.VISIBLE
                : View.INVISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CountriesFilter.containsCountry(selectedCountries, countryCode)) {
                    CountriesFilter.removeCountry(selectedCountries, countryCode);
                } else {
                    CountriesFilter.addCountry(selectedCountries, countryCode);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return allCountries.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();

                if (query.isEmpty()) {
                    results.values = CountriesFilter.allCountries;
                    results.count = CountriesFilter.allCountries.size();
                } else {
                    List<String> filteredItems = new ArrayList<>();
                    for (String item : CountriesFilter.allCountries) {
                        if (item.toLowerCase().contains(query)) {
                            filteredItems.add(item);
                        }
                    }
                    results.values = filteredItems;
                    results.count = filteredItems.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                allCountries = Objects.nonNull(results.values) ? (ArrayList<String>) results.values : new ArrayList<>();
                notifyDataSetChanged();
            }
        };
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCountryBinding binding;

        public ViewHolder(ItemCountryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
