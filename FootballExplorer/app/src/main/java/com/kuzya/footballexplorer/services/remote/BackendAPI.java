package com.kuzya.footballexplorer.services.remote;

import com.kuzya.footballexplorer.Const;
import com.kuzya.footballexplorer.model.season.SeasonsModel;
import com.kuzya.footballexplorer.model.team.TeamsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by kuzya on 21.04.2016.
 */
public interface BackendAPI {

    //TODO use one client for whole app or add header in Interceptor

    @Headers("X-Auth-Token:" + Const.AUTH_TOKEN)
    @GET("soccerseasons")
    Call<List<SeasonsModel>> listSeasons();

    @Headers("X-Auth-Token:" + Const.AUTH_TOKEN)
    @GET("soccerseasons/{id}/teams")
    Call<TeamsModel> listTeams(@Path("id") String seasonId);

    @Headers("X-Auth-Token:" + Const.AUTH_TOKEN)
    @GET("soccerseasons/{id}")
    Call<SeasonsModel> getSeasonInfo(@Path("id") String seasonId);

}
