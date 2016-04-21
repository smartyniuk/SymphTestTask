package com.kuzya.footballexplorer.ui;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.kuzya.footballexplorer.R;
import com.kuzya.footballexplorer.model.team.Team;
import com.kuzya.footballexplorer.ui.svg.SvgDecoder;
import com.kuzya.footballexplorer.ui.svg.SvgDrawableTranscoder;
import com.kuzya.footballexplorer.ui.svg.SvgSoftwareLayerSetter;

import java.io.InputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kuzya on 21.04.2016.
 */
public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.MyViewHolder> {

    private final List<Team> mTeamsList;
    private OnItemClickListener mItemClickListener;
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    public TeamsAdapter(List<Team> mTeamsList, OnItemClickListener clickListener, Context context) {
        this.mTeamsList = mTeamsList;
        this.mItemClickListener = clickListener;

        requestBuilder = Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_error)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_team, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Team teamModel = mTeamsList.get(position);
        holder.txtSquadMarketValue.setText(teamModel.getSquadMarketValue());
        holder.txtTeamName.setText(teamModel.getName());

        holder.imgTeamIcon.setImageResource(R.drawable.image_loading);

        Uri uri = Uri.parse(teamModel.getCrestUrl());
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                // SVG cannot be serialized so it's not worth to cache it
                .load(uri)
                .into(holder.imgTeamIcon);

        holder.model = teamModel;
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mTeamsList.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(Team model);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Team model;

        @Bind(R.id.imgTeamIcon)
        protected ImageView imgTeamIcon;
        @Bind(R.id.txtTeamName)
        protected TextView txtTeamName;
        @Bind(R.id.txtSquadMarketValue)
        protected TextView txtSquadMarketValue;

        @Bind(R.id.layoutItem)
        protected ViewGroup layoutItem;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            layoutItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(model);
            }
        }
    }

}
