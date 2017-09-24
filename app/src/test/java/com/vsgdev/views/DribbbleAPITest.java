package com.vsgdev.views;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.vsgdev.utils.AppConstants;

import org.json.JSONArray;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DribbbleAPITest {

    @Test
    public void shouldTestAPIRequest() throws Exception {

        new JsonArrayRequest(Request.Method.GET, AppConstants.DRIBBBLE_GET_SHOOTS_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                assertNotNull(response);
                assertTrue(response.length() > 0);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}