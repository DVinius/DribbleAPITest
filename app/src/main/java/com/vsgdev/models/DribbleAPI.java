package com.vsgdev.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;


/**
 * Created by franklin on 23/09/17.
 */

public class DribbleAPI {
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

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
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
