package com.kuzya.footballexplorer.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class TeamsModel {

    @SerializedName("_links")
    @Expose
    private com.kuzya.footballexplorer.model.team.Links Links;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("teams")
    @Expose
    private List<Team> teams = new ArrayList<Team>();

    /**
     * @return The Links
     */
    public com.kuzya.footballexplorer.model.team.Links getLinks() {
        return Links;
    }

    /**
     * @param Links The _links
     */
    public void setLinks(com.kuzya.footballexplorer.model.team.Links Links) {
        this.Links = Links;
    }

    /**
     * @return The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return The teams
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * @param teams The teams
     */
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

}
