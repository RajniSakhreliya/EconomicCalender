package com.pomanager.economiccalender.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pomanager.economiccalender.Adapter.AdapterTimeZone;
import com.pomanager.economiccalender.Utils.PreferenceManager;
import com.pomanager.economiccalender.Utils.TimeFormatter;
import com.pomanager.economiccalender.databinding.DialogFilterBinding;
import com.pomanager.economiccalender.databinding.DialogTimezoneBinding;

import java.util.Arrays;
import java.util.Objects;

public class DialogSelectTimeZone extends Dialog {
    DialogTimezoneBinding binding;
    Context context;
    private AdapterTimeZone adapter;
    DialogListener listener;

    public DialogSelectTimeZone(@NonNull Context context, DialogListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogTimezoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.BOTTOM);

        binding.rcvTimeZone.setLayoutManager(new LinearLayoutManager(context));
        adapter = new AdapterTimeZone(context);
        binding.rcvTimeZone.setAdapter(adapter);

        binding.rcvTimeZone.smoothScrollToPosition(Arrays.asList(TimeFormatter.timeZonAdds)
                .indexOf(PreferenceManager.getInstance(context).getSelectedTimezone()));

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.getInstance(context).setSelectedTimezone(adapter.getSelectedTimeZone());
                if (listener != null) listener.onDataChange();
                dismiss();
            }
        });
    }
}
