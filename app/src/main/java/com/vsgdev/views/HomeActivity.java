package com.vsgdev.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.vsgdev.models.DribbleAPI;
import com.vsgdev.models.Shoot;
import com.vsgdev.utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final String url = DribbleAPI.getInstance(this).getShootsListURL();

        final JsonArrayRequest jsonArrayRequest = getShootsJsonArrayRequest(this, url);

        DribbleAPI.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    /**
     * Creates jsonArrayRequest object to get a list of Shoots from Dribbble API
     *
     * @param context the Context
     * @param url Dribble shoots List API url
     * @return JsonArrayRequest ready to be made
     */
    private JsonArrayRequest getShootsJsonArrayRequest(final Context context, final String url){
        return new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                final List<Shoot> shoots = new ArrayList<>(30);

                try{
                    for (int index = 0; index < response.length(); index++){
                        final JSONObject jsonObject = response.getJSONObject(index);
                        final Shoot shoot = DribbleAPI.getInstance(context).convertFromJsonObject(jsonObject);
                        if (shoot != null){
                            shoots.add(shoot);
                            if (index == 29){
                                break;
                            }
                        }
                    }

                } catch (JSONException jsex){
                    Toast.makeText(getApplicationContext(), jsex.getMessage(), Toast.LENGTH_LONG).show();
                }

                DribbleAPI.getInstance(context).setShoots(shoots);

                final ShootViewAdapter shootViewAdapter = new ShootViewAdapter(context, shoots);
                final ListView shootsList = findViewById(R.id.shootsList);
                shootsList.setAdapter(shootViewAdapter);
                shootsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        final Intent intent = new Intent(context, ShootDetailActivity.class);
                        intent.putExtra(AppConstants.SHOOT_POSITION, position);
                        startActivity(intent);
                    }
                });

                final ProgressBar progressBarHome = findViewById(R.id.homeProgress);
                progressBarHome.setVisibility(View.INVISIBLE);
                shootsList.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error during JsonObjectRequest: "+error, Toast.LENGTH_LONG).show();
            }
        });
    }

}

