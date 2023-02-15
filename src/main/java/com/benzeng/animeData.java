package com.benzeng;

public class animeData implements Editable { //Cell setup for table
    public static final String[] STATUS_LIST = {"Watching", "Completed", "Dropped"};
    public static final int STATUS_INDEX = 1;
    private String animeName;
    private String status;
    private String seasonCount;
    private String episodeCount;
    private String rating;

//    public animeData () {
//    }
//
//    public animeData(String animeName, String status, String seasonCount, String episodeCount, String rating) {
//        this.animeName = animeName;
//        this.status = status;
//        this.seasonCount = seasonCount;
//        this.episodeCount = episodeCount;
//        this.rating = rating;
//    }

    @DisplayAs(value = "AnimeName", index = 0, editable = true)
    public String getAnimeName() {
        return animeName;
    }

    public void setAnimeName(String animeName) {
        this.animeName = animeName;
    }

    @DisplayAs(value = "Status", index = STATUS_INDEX, editable = true)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @DisplayAs(value = "Seasons", index = 2, editable = true)
    public String getSeasonCount() {
        return seasonCount;
    }

    public void setSeasonCount(String seasonCount) {
        this.seasonCount = seasonCount;
    }

    @DisplayAs(value = "Episodes", index = 3, editable = true)
    public String getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(String episodeCount) {
        this.episodeCount = episodeCount;
    }

    @DisplayAs(value = "Rating", index = 4, editable = true)
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public boolean isEditable() {
        return true;
    }
}
