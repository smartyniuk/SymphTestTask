package com.kuzya.footballexplorer.services.local;

import com.kuzya.footballexplorer.model.season.SeasonsModel;

import java.util.List;

/**
 * Created by kuzya on 21.04.2016.
 */
public interface ILocalStorage {
    void saveSeasons(List<SeasonsModel> seasonsModelList);

    List<SeasonsModel> loadSeasons();
}
