package com.example.cloudcards;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;


public class APIHelper {

    HttpURLConnection connection;
    Context context;
    private Card newCard;

    public APIHelper(Context context){
        this.context = context;
    }

    public void getCardByName(String name, VolleyCallback callback){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://api.magicthegathering.io/v1/cards?name="+name,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("response", response.toString());
                        JSONObject cardData = null;
                        try {
                            cardData = response.getJSONArray("cards").getJSONObject(0);
                            int multi = cardData.getInt("multiverseid");
                            String imageURL = cardData.getString("imageUrl");
                            Log.i("multiverse", ""+multi);
                        }catch (JSONException a){

                        }
                        callback.onSuccess(cardData);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);
    }
}