package com.kuzya.footballexplorer.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Links {

    @SerializedName("self")
    @Expose
    private Self self;
    @SerializedName("soccerseason")
    @Expose
    private Soccerseason soccerseason;

    /**
     * @return The self
     */
    public Self getSelf() {
        return self;
    }

    /**
     * @param self The self
     */
    public void setSelf(Self self) {
        this.self = self;
    }

    /**
     * @return The soccerseason
     */
    public Soccerseason getSoccerseason() {
        return soccerseason;
    }

    /**
     * @param soccerseason The soccerseason
     */
    public void setSoccerseason(Soccerseason soccerseason) {
        this.soccerseason = soccerseason;
    }

}
