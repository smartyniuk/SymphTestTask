package com.kuzya.footballexplorer.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kuzya.footballexplorer.R;
import com.kuzya.footballexplorer.model.season.SeasonsModel;
import com.kuzya.footballexplorer.model.team.Team;
import com.kuzya.footballexplorer.model.team.TeamsModel;
import com.kuzya.footballexplorer.ui.TeamItemsDecoration;
import com.kuzya.footballexplorer.ui.TeamsAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsActivity extends BaseActivity {
    public static final String SEASON_ID = "season_id";
    private static final Logger mLogger = LoggerFactory.getLogger(TeamsActivity.class);
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;
    @Bind(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    @Bind(R.id.swipeContainer)
    protected SwipeRefreshLayout mSwipeContainer;
    @Bind(R.id.layoutInfo)
    protected ViewGroup layoutSeasonHeader;
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
    @Bind(R.id.collapsing_toolbar)
    protected CollapsingToolbarLayout collapsingToolbar;

    private TeamsAdapter mAdapter;
    private List<Team> mTeamsList = new ArrayList<>();
    private String mSeasonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar aBar = getSupportActionBar();
        aBar.setDisplayHomeAsUpEnabled(true);
        aBar.setHomeButtonEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAdapter = new TeamsAdapter(mTeamsList, new TeamsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Team model) {
                Toast.makeText(TeamsActivity.this, "Team clicked: " + model.getName(), Toast.LENGTH_SHORT).show();
            }
        }, getBaseContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new TeamItemsDecoration(
                getApplicationContext()
        ));

        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTeams(mSeasonId);
            }
        });

        Intent intent = getIntent();
        mSeasonId = intent.getStringExtra(SEASON_ID);

        mLogger.info("Season: " + mSeasonId);

        layoutSeasonHeader.setVisibility(View.INVISIBLE);
        collapsingToolbar.setTitle("");

        loadSeasonInfo(mSeasonId);
        loadTeams(mSeasonId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(TeamsActivity.SEASON_ID, mSeasonId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mSeasonId = savedInstanceState.getString(TeamsActivity.SEASON_ID);

        mLogger.info(mSeasonId);
    }

    private void loadSeasonInfo(String seasonId) {
        Call<SeasonsModel> call = _mBackendAPI.getSeasonInfo(seasonId);
        call.enqueue(new Callback<SeasonsModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<SeasonsModel> call, Response<SeasonsModel> response) {
                Boolean isSuccessful = response.isSuccessful();
                if (isSuccessful) {
                    SeasonsModel seasonsModel = response.body();
                    layoutSeasonHeader.setVisibility(View.VISIBLE);
                    collapsingToolbar.setTitle(seasonsModel.getCaption());
                    txtCurrentMatchDay.setText(seasonsModel.getCurrentMatchday().toString());
                    txtLastUpdated.setText(seasonsModel.getLastUpdated().replace("T", " ").replace("Z", " "));
                    txtLeague.setText(seasonsModel.getLeague());
                    txtNumberOfGames.setText(seasonsModel.getNumberOfGames().toString());
                    txtNumberOfMatchDays.setText(seasonsModel.getNumberOfMatchdays().toString());
                    txtNumbersOfTeams.setText(seasonsModel.getNumberOfTeams().toString());
                    txtYear.setText(seasonsModel.getYear());
                } else {
                    try {
                        onError(response.errorBody().string());
                    } catch (IOException e) {
                        onError(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<SeasonsModel> call, Throwable t) {
                onError("Request failed: " + call.request().url());
            }
        });
    }

    private void loadTeams(String seasonId) {
        Call<TeamsModel> call = _mBackendAPI.listTeams(seasonId);
        call.enqueue(new Callback<TeamsModel>() {
            @Override
            public void onResponse(Call<TeamsModel> call, Response<TeamsModel> response) {
                Boolean isSuccessful = response.isSuccessful();

                if (isSuccessful) {
                    List<Team> teams = response.body().getTeams();
                    mTeamsList.clear();
                    mTeamsList.addAll(teams);
                    mAdapter.notifyDataSetChanged();
                } else {
                    try {
                        onError(response.errorBody().string());
                    } catch (IOException e) {
                        onError(e.toString());
                    }
                }

                mSwipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<TeamsModel> call, Throwable t) {
                onError("Request failed: " + call.request().url());
            }
        });
    }

    private void onError(String msg) {
        mLogger.error(msg);
        Toast.makeText(TeamsActivity.this, msg, Toast.LENGTH_SHORT).show();
        mSwipeContainer.setRefreshing(false);
    }
}
