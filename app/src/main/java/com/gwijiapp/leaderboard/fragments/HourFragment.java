package com.gwijiapp.leaderboard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gwijiapp.leaderboard.R;
import com.gwijiapp.leaderboard.adapters.HourLeaderRecyclerViewAdapter;
import com.gwijiapp.leaderboard.configs.AppConfig;
import com.gwijiapp.leaderboard.models.Hour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HourFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Hour> hours;
    private HourLeaderRecyclerViewAdapter hourLeaderRecyclerViewAdapter;



    public HourFragment() {
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_hour, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.hour_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        hours=new ArrayList<>();

        hourLeaderRecyclerViewAdapter=new HourLeaderRecyclerViewAdapter(hours,getActivity());
        recyclerView.setAdapter(hourLeaderRecyclerViewAdapter);

        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh);



        getHours();

        return  view;
    }

    private void getHours() {

        swipeRefreshLayout.setRefreshing(true);

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, AppConfig.HOUR_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                swipeRefreshLayout.setRefreshing(false);

                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject object=response.getJSONObject(i);
                        String name=object.getString("name");
                        String badgeUrl=object.getString("badgeUrl");
                        int hourTotal=object.getInt("hours");
                        String country=object.getString("country");

                        Hour hour=new Hour(name,hourTotal,country,badgeUrl);
                        hours.add(hour);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                hourLeaderRecyclerViewAdapter.notifyDataSetChanged();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsonArrayRequest.setRetryPolicy(policy);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }
}