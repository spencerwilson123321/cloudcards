package com.example.cloudcards;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
// import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;

public class APIHelper {

    HttpURLConnection connection;
    Context context;
    // private Card newCard;

    public APIHelper(Context context){
        this.context = context;
    }

    public void getCardByName(String name, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        List<String> a = new ArrayList<>();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.magicthegathering.io")
                .appendPath("v1")
                .appendPath("cards")
                .appendQueryParameter("name", name);
        String requestURI = builder.toString();
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                requestURI,
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
