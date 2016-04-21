package com.kuzya.footballexplorer.services.local;

import com.kuzya.footballexplorer.model.season.SeasonsModel;

import java.util.List;

/**
 * Created by kuzya on 21.04.2016.
 */
public class DBLocalStorage implements ILocalStorage {
    @Override
    public void saveSeasons(List<SeasonsModel> seasonsModelList) {
        //TODO save to local base
    }

    @Override
    public List<SeasonsModel> loadSeasons() {
        //TODO read from local base
        return null;
    }
}
