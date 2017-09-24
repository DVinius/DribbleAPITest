package com.vsgdev.views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.squareup.picasso.Picasso;
import com.vsgdev.models.DribbleAPI;
import com.vsgdev.models.Shoot;
import com.vsgdev.utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private final SimpleDateFormat dribbleDateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final SimpleDateFormat appDateFmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final String url = "https://api.dribbble.com/v1/shots?sort=recent&"+getAccessToken();
        final Context context = this;

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

                final List<Shoot> shoots = new ArrayList<>(30);

                try{
                    for (int index = 0; index < response.length(); index++){
                        final JSONObject jsonObject = response.getJSONObject(index);

                        final String title = jsonObject.getString(AppConstants.SHOOT_TITLE);
                        final Integer viewCount = jsonObject.getInt(AppConstants.SHOOT_VIEWS_COUNT);
                        final String createdAt = jsonObject.getString(AppConstants.SHOOT_CREATED_AT).replace('T',' ').replace("Z","");
                        final Date resultDate = getCreatedAtAsDate(createdAt);
                        final String resultStrDate = getDateStr(resultDate);


                        final Shoot shoot = new Shoot(title, resultStrDate, viewCount);

                        final Integer commentsCount = jsonObject.getInt(AppConstants.SHOOT_COMMENTS_COUNT);
                        shoot.setCommentsCount(commentsCount);

                        final String description = jsonObject.getString(AppConstants.SHOOT_DESCRIPTION);
                        shoot.setDescription(description);

                        final String shootTeaserImageUrl = jsonObject.getJSONObject(AppConstants.SHOOT_IMAGES).getString(AppConstants.SHOOT_TEASER_IMAGE);
                        shoot.setTeaserImageUrl(shootTeaserImageUrl);

                        final String shootNormalImageUrl = jsonObject.getJSONObject(AppConstants.SHOOT_IMAGES).getString(AppConstants.SHOOT_NORMAL_IMAGE);
                        shoot.setNormalImageUrl(shootNormalImageUrl);


                        shoots.add(shoot);

                        if (index == 29){
                            break;
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
                Toast.makeText(getApplicationContext(),
                        "Error during JsonObjectRequest: "+error, Toast.LENGTH_LONG)
                        .show();
            }
        });

        DribbleAPI.getInstance(this).addToRequestQueue(jsonArrayRequest);

    }



    private String getAccessToken(){
        return "access_token=ebed03191c0ead058db6b9063520775351610bc94526924f2a02e6caf74a87d1";
    }

    protected Date getCreatedAtAsDate(final String dateStr){
        if (dateStr != null){
            try{
                return dribbleDateFmt.parse(dateStr);
            } catch (ParseException pex){
                return null;
            }
        }
        return null;
    }

    protected String getDateStr(final Date date){
        return appDateFmt.format(date);
    }
}

