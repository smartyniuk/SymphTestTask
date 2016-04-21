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

import com.google.common.eventbus.Subscribe;
import com.kuzya.footballexplorer.R;
import com.kuzya.footballexplorer.model.season.SeasonsModel;
import com.kuzya.footballexplorer.services.local.AppPrefs;
import com.kuzya.footballexplorer.services.remote.FootballService;
import com.kuzya.footballexplorer.ui.SeasonsAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final Logger mLogger = LoggerFactory.getLogger(MainActivity.class);

    @Bind(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    @Bind(R.id.swipeContainer)
    protected SwipeRefreshLayout mSwipeContainer;
    @Bind(R.id.collapsing_toolbar)
    protected CollapsingToolbarLayout collapsingToolbar;

    @Inject
    protected AppPrefs mAppPrefs;

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
    }

    private void loadSeasons() {
        mSwipeContainer.setRefreshing(true);
        mFootballService.listSeasons();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFootballService.eventBus.register(this);

        loadSeasons();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFootballService.eventBus.unregister(this);
    }

    @Subscribe
    public void onSeasonsLoaded(FootballService.LoadSeasonsEvent event) {
        mLogger.debug("Seasons loaded: {}", event.getSeasons().size());
        mSeasonsList.clear();
        mSeasonsList.addAll(event.getSeasons());
        mAdapter.notifyDataSetChanged();
    }

    private void showTeams(SeasonsModel model) {
        Intent intent = new Intent(this, TeamsActivity.class);
        intent.putExtra(TeamsActivity.SEASON_ID, model.getId().toString());

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        //TODO preferences
        return true;
    }
}
