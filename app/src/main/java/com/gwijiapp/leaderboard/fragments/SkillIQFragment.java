package com.gwijiapp.leaderboard.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.gwijiapp.leaderboard.R;
import com.gwijiapp.leaderboard.adapters.SkillIQRecyclerViewAdapter;
import com.gwijiapp.leaderboard.configs.AppConfig;
import com.gwijiapp.leaderboard.models.SkillIQ;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SkillIQFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<SkillIQ> skillIQList;
    private SkillIQRecyclerViewAdapter skillIQRecyclerViewAdapter;




    public SkillIQFragment() {
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_skill, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.skill_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        skillIQList=new ArrayList<>();

        skillIQRecyclerViewAdapter=new SkillIQRecyclerViewAdapter(skillIQList,getActivity());
        recyclerView.setAdapter(skillIQRecyclerViewAdapter);

        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh);



        getSkillIQ();

        return  view;
    }

    private void getSkillIQ(){
        swipeRefreshLayout.setRefreshing(true);

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, AppConfig.SKILL_IQ_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                swipeRefreshLayout.setRefreshing(false);

                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject object=response.getJSONObject(i);
                        String name=object.getString("name");
                        String badgeUrl=object.getString("badgeUrl");
                        int score=object.getInt("score");
                        String country=object.getString("country");

                        SkillIQ skillIQ=new SkillIQ(name,score,country,badgeUrl);
                        skillIQList.add(skillIQ);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                skillIQRecyclerViewAdapter.notifyDataSetChanged();



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