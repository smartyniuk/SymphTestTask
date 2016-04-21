package com.kuzya.footballexplorer.services.remote;

/**
 * Created by kuzya on 21.04.2016.
 */
public class FootballService {
    private static final String AUTH_TOKEN = "617b810b5d7d40c7ab40f18155937bf3";
    private final BackendAPI mApi;

    public FootballService(BackendAPI api) {
        this.mApi = api;
    }

   /* Call<List<SeasonsModel>> listSeasons();


    Call<TeamsModel> listTeams(@Path("id") String seasonId);


    Call<SeasonsModel> getSeasonInfo(@Path("id") String seasonId);*/
}
