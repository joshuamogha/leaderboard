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
import com.gwijiapp.leaderboard.models.SkillIQ;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SkillIQRecyclerViewAdapter extends RecyclerView.Adapter<SkillIQRecyclerViewAdapter.SkillIQViewHolder> {
   private List<SkillIQ> skillIQList;
   private Context context;

   public SkillIQRecyclerViewAdapter(List<SkillIQ> skillIQList,Context context){
       this.skillIQList=skillIQList;
       this.context=context;
   }

    @NonNull
    @Override
    public SkillIQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_row, parent,false);

        return new SkillIQViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillIQViewHolder holder, int position) {

       SkillIQ skillIQ=skillIQList.get(position);
       holder.nameTv.setText(skillIQ.getName());
       holder.scoreHourTv.setText(String.valueOf(skillIQ.getScore())+" Skill IQ Score, "+ skillIQ.getCountry());

        Picasso.get()
                .load(skillIQ.getBadgeUrl())
                .into(holder.badgeIv);


    }

    @Override
    public int getItemCount() {
        return skillIQList.size();
    }

    public  class SkillIQViewHolder extends RecyclerView.ViewHolder{
       TextView nameTv,scoreHourTv;
       ImageView badgeIv;

        public SkillIQViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTv=itemView.findViewById(R.id.name);
            scoreHourTv=itemView.findViewById(R.id.score_country_tv);
            badgeIv=itemView.findViewById(R.id.badgeUrl);

        }
    }
}
