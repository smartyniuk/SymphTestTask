package com.kuzya.footballexplorer.ui;

import android.annotation.SuppressLint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuzya.footballexplorer.R;
import com.kuzya.footballexplorer.model.season.SeasonsModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kuzya on 21.04.2016.
 */
public class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.MyViewHolder> {

    private final List<SeasonsModel> seasonsList;
    private OnItemClickListener mItemClickListener;

    public SeasonsAdapter(List<SeasonsModel> seasonsList, OnItemClickListener clickListener) {
        this.seasonsList = seasonsList;
        this.mItemClickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_season, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SeasonsModel seasonsModel = seasonsList.get(position);
        holder.txtCaption.setText(seasonsModel.getCaption());
        holder.txtCurrentMatchDay.setText(seasonsModel.getCurrentMatchday().toString());
        holder.txtLastUpdated.setText(seasonsModel.getLastUpdated().replace("T", " ").replace("Z", " "));
        holder.txtLeague.setText(seasonsModel.getLeague());
        holder.txtNumberOfGames.setText(seasonsModel.getNumberOfGames().toString());
        holder.txtNumberOfMatchDays.setText(seasonsModel.getNumberOfMatchdays().toString());
        holder.txtNumbersOfTeams.setText(seasonsModel.getNumberOfTeams().toString());
        holder.txtYear.setText(seasonsModel.getYear());

        holder.model = seasonsModel;
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return seasonsList.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(SeasonsModel model);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public SeasonsModel model;
        @Bind(R.id.txtCaption)
        protected TextView txtCaption;
        @Bind(R.id.txtCurrentMatchDay)
        protected TextView txtCurrentMatchDay;
        @Bind(R.id.txtLastUpdated)
        protected TextView txtLastUpdated;
        @Bind(R.id.txtLeague)
        protected TextView txtLeague;
        @Bind(R.id.txtNumberOfGames)
        protected TextView txtNumberOfGames;
        @Bind(R.id.txtNumberOfMatchDays)
        protected TextView txtNumberOfMatchDays;
        @Bind(R.id.txtNumbersOfTeams)
        protected TextView txtNumbersOfTeams;
        @Bind(R.id.txtYear)
        protected TextView txtYear;
        @Bind(R.id.cardView)
        protected CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(model);
            }
        }
    }

}
