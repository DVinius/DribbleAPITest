package com.vsgdev.models;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.vsgdev.utils.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Singleton Android Application pattern.
 *
 * Created by franklin on 23/09/17.
 */

public class DribbleAPI {
    private static final SimpleDateFormat dribbleDateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat appDateFmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static DribbleAPI instance;
    private RequestQueue requestQueue;
    private static Context ctx;
    private List<Shoot> shoots;

    private DribbleAPI(Context context) {
        this.ctx = context;
        this.requestQueue = getRequestQueue();
    }

    public static synchronized DribbleAPI getInstance(Context context) {
        if (instance == null) {
            instance = new DribbleAPI(context);
        }
        return instance;
    }

    /**
     * Returns single requestQueue instace to make volley requests.
     *
     * @return requestQueue to receive request objects
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Provides Dribbble URL to get List of Shoots as JsonArray.
     *
     * @return Dribbble string url
     */
    public String getShootsListURL(){
        return AppConstants.DRIBBBLE_GET_SHOOTS_URL;
    }

    /**
     * Converts from JsonObject to Shoot object.
     *
     * @param jsonObject the object to be converted
     * @return a Shoot instace filled with json data.
     */
    public static Shoot convertFromJsonObject(final JSONObject jsonObject){
        try{
            final String title = jsonObject.getString(AppConstants.SHOOT_TITLE);
            final Integer viewCount = jsonObject.getInt(AppConstants.SHOOT_VIEWS_COUNT);
            final String createdAt = jsonObject.getString(AppConstants.SHOOT_CREATED_AT).replace('T',' ').replace("Z","");
            final Integer commentsCount = jsonObject.getInt(AppConstants.SHOOT_COMMENTS_COUNT);
            final String description = jsonObject.getString(AppConstants.SHOOT_DESCRIPTION);
            final String shootTeaserImageUrl = jsonObject.getJSONObject(AppConstants.SHOOT_IMAGES).getString(AppConstants.SHOOT_TEASER_IMAGE);
            final String shootNormalImageUrl = jsonObject.getJSONObject(AppConstants.SHOOT_IMAGES).getString(AppConstants.SHOOT_NORMAL_IMAGE);

            final Date resultDate = getCreatedAtAsDate(createdAt);
            final String resultStrDate = getDateStr(resultDate);

            final Shoot shoot = new Shoot(title, resultStrDate, viewCount);
            shoot.setCommentsCount(commentsCount);
            shoot.setDescription(description);
            shoot.setTeaserImageUrl(shootTeaserImageUrl);
            shoot.setNormalImageUrl(shootNormalImageUrl);

            return shoot;

        } catch (JSONException jsex){

        }
        return null;
    }

    public static Date getCreatedAtAsDate(final String dateStr){
        if (dateStr != null){
            try{
                return dribbleDateFmt.parse(dateStr);
            } catch (ParseException pex){
                return null;
            }
        }
        return null;
    }

    public static String getDateStr(final Date date){
        return appDateFmt.format(date);
    }


    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public List<Shoot> getShoots() {
        return shoots;
    }

    public void setShoots(List<Shoot> shoots) {
        this.shoots = shoots;
    }
}
