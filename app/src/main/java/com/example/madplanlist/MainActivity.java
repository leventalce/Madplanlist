package com.example.madplanlist;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Year;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ListView myListView;
    String[] items;
    String[] descriptions;

    private RequestQueue mQueue;
    int year = Calendar.getInstance().get(Calendar.YEAR);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);

        String ApiUrl = "http://myresume.dk/SanbotWebsite/php/API/MadplanAPI.php?year=" + year + "&week=" + year;

        JsonObjectRequest EventRequest = new JsonObjectRequest(Request.Method.GET, ApiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Madplan");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        mQueue.add(EventRequest);



        Resources res = getResources();
        myListView = (ListView) findViewById(R.id.MyListView);
        items = res.getStringArray(R.array.Items);
        descriptions = res.getStringArray(R.array.descriptions);

        ItemAdapter itemAdapter = new ItemAdapter(this, items, descriptions);
        myListView.setAdapter(itemAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent ShowDetailActivity =
                        new Intent(getApplicationContext(), DetailActivity.class);
                ShowDetailActivity.putExtra("com.example.madplanlist.ITEM_INDEX", i);
                startActivity(ShowDetailActivity);
            }
        });



    }
}
