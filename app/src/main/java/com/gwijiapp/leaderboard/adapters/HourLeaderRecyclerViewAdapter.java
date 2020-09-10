package com.gwijiapp.leaderboard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gwijiapp.leaderboard.R;
import com.gwijiapp.leaderboard.models.Hour;
import com.gwijiapp.leaderboard.models.SkillIQ;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HourLeaderRecyclerViewAdapter extends RecyclerView.Adapter<HourLeaderRecyclerViewAdapter.HourLeaderViewHolder> {
    private List<Hour> hours;
    private Context context;

    public HourLeaderRecyclerViewAdapter(List<Hour> hours,Context context){
        this.context=context;
        this.hours=hours;
    }

    @NonNull
    @Override
    public HourLeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_row, parent,false);

        return new HourLeaderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HourLeaderViewHolder holder, int position) {
        Hour hour=hours.get(position);
        holder.nameTv.setText(hour.getName());
        holder.scoreHourTv.setText(String.valueOf(hour.getHours())+" learning hours, "+ hour.getCountry());

        Picasso.get()
                .load(hour.getBadgeUrl())
                .into(holder.badgeIv);
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public  class HourLeaderViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv,scoreHourTv;
        ImageView badgeIv;
        public HourLeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTv=itemView.findViewById(R.id.name);
            scoreHourTv=itemView.findViewById(R.id.score_country_tv);
            badgeIv=itemView.findViewById(R.id.badgeUrl);
        }
    }
}
