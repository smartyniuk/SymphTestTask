package com.kuzya.footballexplorer.model.season;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class SeasonsModel {

    @SerializedName("_links")
    @Expose
    private com.kuzya.footballexplorer.model.season.Links Links;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("league")
    @Expose
    private String league;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("currentMatchday")
    @Expose
    private Integer currentMatchday;
    @SerializedName("numberOfMatchdays")
    @Expose
    private Integer numberOfMatchdays;
    @SerializedName("numberOfTeams")
    @Expose
    private Integer numberOfTeams;
    @SerializedName("numberOfGames")
    @Expose
    private Integer numberOfGames;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;

    /**
     * @return The Links
     */
    public com.kuzya.footballexplorer.model.season.Links getLinks() {
        return Links;
    }

    /**
     * @param Links The _links
     */
    public void setLinks(com.kuzya.footballexplorer.model.season.Links Links) {
        this.Links = Links;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * @param caption The caption
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * @return The league
     */
    public String getLeague() {
        return league;
    }

    /**
     * @param league The league
     */
    public void setLeague(String league) {
        this.league = league;
    }

    /**
     * @return The year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year The year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return The currentMatchday
     */
    public Integer getCurrentMatchday() {
        return currentMatchday;
    }

    /**
     * @param currentMatchday The currentMatchday
     */
    public void setCurrentMatchday(Integer currentMatchday) {
        this.currentMatchday = currentMatchday;
    }

    /**
     * @return The numberOfMatchdays
     */
    public Integer getNumberOfMatchdays() {
        return numberOfMatchdays;
    }

    /**
     * @param numberOfMatchdays The numberOfMatchdays
     */
    public void setNumberOfMatchdays(Integer numberOfMatchdays) {
        this.numberOfMatchdays = numberOfMatchdays;
    }

    /**
     * @return The numberOfTeams
     */
    public Integer getNumberOfTeams() {
        return numberOfTeams;
    }

    /**
     * @param numberOfTeams The numberOfTeams
     */
    public void setNumberOfTeams(Integer numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    /**
     * @return The numberOfGames
     */
    public Integer getNumberOfGames() {
        return numberOfGames;
    }

    /**
     * @param numberOfGames The numberOfGames
     */
    public void setNumberOfGames(Integer numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    /**
     * @return The lastUpdated
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     * @param lastUpdated The lastUpdated
     */
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
