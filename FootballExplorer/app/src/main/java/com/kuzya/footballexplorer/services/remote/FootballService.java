package com.kuzya.footballexplorer.services.remote;

import android.content.Context;
import android.widget.Toast;

import com.google.common.eventbus.EventBus;
import com.kuzya.footballexplorer.model.season.SeasonsModel;
import com.kuzya.footballexplorer.model.team.TeamsModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kuzya on 21.04.2016.
 */
public class FootballService {
    private static final Logger mLogger = LoggerFactory.getLogger(FootballService.class);
    //TODO replace EventBus with code generated framework
    public final EventBus eventBus = new EventBus();
    private final BackendAPI mApi;
    private Context mContext;


    public FootballService(BackendAPI api, Context context) {
        this.mApi = api;
        mContext = context;
    }

    public void listSeasons() {
        Call<List<SeasonsModel>> call = mApi.listSeasons();

        call.enqueue(new Callback<List<SeasonsModel>>() {
            @Override
            public void onResponse(Call<List<SeasonsModel>> call, Response<List<SeasonsModel>> response) {
                if (response.isSuccessful()) {
                    eventBus.post(new LoadSeasonsEvent(response.body()));
                } else {
                    try {
                        onError(response.errorBody().string());
                    } catch (IOException e) {
                        onError(e.toString());
                    }
                    eventBus.post(new LoadSeasonsEvent(new ArrayList<SeasonsModel>()));
                }
            }

            @Override
            public void onFailure(Call<List<SeasonsModel>> call, Throwable t) {
                onError("Request failed: " + call.request().url());
                //TODO handle error
                eventBus.post(new LoadSeasonsEvent(new ArrayList<SeasonsModel>()));
            }
        });
    }

    public void listTeams(String seasonId) {
        Call<TeamsModel> call = mApi.listTeams(seasonId);
        call.enqueue(new Callback<TeamsModel>() {
            @Override
            public void onResponse(Call<TeamsModel> call, Response<TeamsModel> response) {

                if (response.isSuccessful()) {
                    eventBus.post(new LoadTeamsEvent(response.body(), false));
                } else {
                    try {
                        onError(response.errorBody().string());
                    } catch (IOException e) {
                        onError(e.toString());
                    }

                    eventBus.post(new LoadTeamsEvent(null, true));
                }
            }

            @Override
            public void onFailure(Call<TeamsModel> call, Throwable t) {
                onError("Request failed: " + call.request().url());
                eventBus.post(new LoadTeamsEvent(null, true));
            }
        });
    }

    public void getSeasonInfo(String seasonId) {
        Call<SeasonsModel> call = mApi.getSeasonInfo(seasonId);
        call.enqueue(new Callback<SeasonsModel>() {
            @Override
            public void onResponse(Call<SeasonsModel> call, Response<SeasonsModel> response) {
                if (response.isSuccessful()) {
                    eventBus.post(new LoadSeasonInfoEvent(response.body(), false));
                } else {
                    try {
                        onError(response.errorBody().string());
                    } catch (IOException e) {
                        onError(e.toString());
                    }
                    eventBus.post(new LoadSeasonInfoEvent(null, true));
                }
            }

            @Override
            public void onFailure(Call<SeasonsModel> call, Throwable t) {
                onError("Request failed: " + call.request().url());
                eventBus.post(new LoadSeasonInfoEvent(null, true));
            }
        });
    }

    private void onError(String msg) {
        mLogger.error(msg);
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static class LoadSeasonInfoEvent {
        private SeasonsModel mSeasonInfo;
        private boolean mError;

        public LoadSeasonInfoEvent(SeasonsModel teamsModel, boolean error) {
            this.mSeasonInfo = teamsModel;
            this.mError = error;
        }

        public SeasonsModel getSeasonInfo() {
            return mSeasonInfo;
        }

        public boolean isError() {
            return mError;
        }
    }

    public static class LoadTeamsEvent {
        private TeamsModel mTeamsModel;
        private boolean mError;

        public LoadTeamsEvent(TeamsModel teamsModel, boolean error) {
            this.mTeamsModel = teamsModel;
            this.mError = error;
        }

        public TeamsModel getTeamsMosel() {
            return mTeamsModel;
        }

        public boolean isError() {
            return mError;
        }
    }

    public static class LoadSeasonsEvent {
        private List<SeasonsModel> mSeasons;

        public LoadSeasonsEvent(List<SeasonsModel> seasons) {
            mSeasons = seasons;
        }

        public List<SeasonsModel> getSeasons() {
            return mSeasons;
        }
    }
}
