package com.kuzya.footballexplorer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.kuzya.footballexplorer.R;
import com.kuzya.footballexplorer.model.season.SeasonsModel;
import com.kuzya.footballexplorer.ui.SeasonsAdapter;

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

public class MainActivity extends BaseActivity {

    private static final Logger mLogger = LoggerFactory.getLogger(MainActivity.class);


    @Bind(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    @Bind(R.id.swipeContainer)
    protected SwipeRefreshLayout mSwipeContainer;

    private SeasonsAdapter mAdapter;
    private List<SeasonsModel> mSeasonsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        getBackendComponent().inject(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getBaseContext().getResources().getString(R.string.app_name));


        mAdapter = new SeasonsAdapter(mSeasonsList, new SeasonsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SeasonsModel model) {
                showTeams(model);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);


        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadSeasons();
            }
        });

        loadSeasons();
    }

    private void loadSeasons() {
        Call<List<SeasonsModel>> call = _mBackendAPI.listSeasons();
        mSwipeContainer.setRefreshing(true);

        call.enqueue(new Callback<List<SeasonsModel>>() {
            @Override
            public void onResponse(Call<List<SeasonsModel>> call, Response<List<SeasonsModel>> response) {
                int statusCode = response.code();
                boolean isSuccessful = response.isSuccessful();
                if (isSuccessful) {
                    List<SeasonsModel> seasons = response.body();
                    mLogger.debug("Seasons loaded: {}", seasons.size());
                    mSeasonsList.clear();
                    mSeasonsList.addAll(seasons);
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
            public void onFailure(Call<List<SeasonsModel>> call, Throwable t) {
                onError("Request failed: " + call.request().url());
            }
        });
    }

    private void onError(String msg) {
        mLogger.error(msg);
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        mSwipeContainer.setRefreshing(false);
    }

    private void showTeams(SeasonsModel model) {
        Intent intent = new Intent(this, TeamsActivity.class);
        intent.putExtra(TeamsActivity.SEASON_ID, model.getId().toString());

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
