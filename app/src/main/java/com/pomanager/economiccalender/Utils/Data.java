package com.pomanager.economiccalender.Utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pomanager.economiccalender.Models.ResponseModel;
import com.pomanager.economiccalender.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Data {

    public interface onDataFetchListener {
        void onDataSuccess(ArrayList<ResponseModel> data);

        void onError(String error);
    }

    public static void getData(Context context, String date, String priority, String countries, onDataFetchListener listener) {
        ArrayList<ResponseModel> listItems = new ArrayList<>();
        PreferenceManager preferenceManager = PreferenceManager.getInstance(context);

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.GET,
                preferenceManager.getApi() + "?token=" + preferenceManager.getToken() +
                        "&lang=en" +
                        "&date_to=" + date + " 23:59:00" +
                        "&date_from=" + date + " 00:00:00" +
                        "&priority=" + priority +
                        "&countries=" + countries +
                        "&order=time:asc" +
                        "&per_page=9999" +
                        "&out_type=" + preferenceManager.getOutType(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listItems.clear();

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONObject itemsObject = object.getJSONObject("items");
                            Iterator<String> keys = itemsObject.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject itemObject = itemsObject.getJSONObject(key);

                                Gson gson = new Gson();
                                ResponseModel responseModel = new ResponseModel();
                                try {
                                    responseModel = gson.fromJson(itemObject.toString(), ResponseModel.class);
                                } catch (Exception ignored) {
                                }

                                listItems.add(responseModel);
                            }

                            if (!listItems.isEmpty()) {
                                if (listener != null) listener.onDataSuccess(listItems);
                            } else {
                                if (listener != null)
                                    listener.onError(ContextCompat.getString(context, R.string.empty_data));
                            }
                        } catch (Exception e) {
                            if (listener != null) listener.onError(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) listener.onError(error.toString());
            }
        });

        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(mStringRequest);
    }
}
