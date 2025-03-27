package com.pomanager.economiccalender.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.JsonObject;
import com.pomanager.economiccalender.HowTo.ActivityHowToUse;
import com.pomanager.economiccalender.MyApplication;
import com.pomanager.economiccalender.R;
import com.pomanager.economiccalender.Utils.PreferenceManager;

import org.json.JSONObject;

public class ActivitySplash extends AppCompatActivity {

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;

        if (MyApplication.isOnline(activity)) {
            MyApplication.mFirebaseRemoteConfig
                    .fetchAndActivate()
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            String response = FirebaseRemoteConfig.getInstance()
                                    .getString(MyApplication.KEY_DATA);

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                PreferenceManager.getInstance(activity).setApi(jsonObject.getString("api"));
                                PreferenceManager.getInstance(activity).setToken(jsonObject.getString("token"));
                                PreferenceManager.getInstance(activity).setOutType(jsonObject.getString("out_type"));

                                Intent intent = new Intent(activity, PreferenceManager.getInstance(activity).getIsFirstTime() ? ActivityHowToUse.class : MainActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (Exception e) {
                                Toast.makeText(activity, getResources().getString(R.string.try_again), Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(e -> {
                    });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
        }

    }
}