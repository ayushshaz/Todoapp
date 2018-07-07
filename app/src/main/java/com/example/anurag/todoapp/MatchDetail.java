package com.example.anurag.todoapp;

import android.graphics.Bitmap;


public class MatchDetail {

    private String matchId;
    private String group;
    private String match;
    private String teamOneName;
    private Bitmap teamOneImage;
    private String teamTwoName;
    private Bitmap teamTwoImage;
    private String teamOneImageBase64;
    private String teamTwoImageBase64;

    public MatchDetail(){

    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getTeamOneName() {
        return teamOneName;
    }

    public void setTeamOneName(String teamOneName) {
        this.teamOneName = teamOneName;
    }

    public Bitmap getTeamOneImage() {
        return teamOneImage;
    }

    public void setTeamOneImage(Bitmap teamOneImage) {
        this.teamOneImage = teamOneImage;
    }

    public String getTeamTwoName() {
        return teamTwoName;
    }

    public void setTeamTwoName(String teamTwoName) {
        this.teamTwoName = teamTwoName;
    }

    public Bitmap getTeamTwoImage() {
        return teamTwoImage;
    }

    public void setTeamTwoImage(Bitmap teamTwoImage) {
        this.teamTwoImage = teamTwoImage;
    }

    public String getTeamOneImageBase64() {
        return teamOneImageBase64;
    }

    public void setTeamOneImageBase64(String teamOneImageBase64) {
        this.teamOneImageBase64 = teamOneImageBase64;
    }

    public String getTeamTwoImageBase64() {
        return teamTwoImageBase64;
    }

    public void setTeamTwoImageBase64(String teamTwoImageBase64) {
        this.teamTwoImageBase64 = teamTwoImageBase64;
    }

    @Override
    public String toString() {
        return getMatchId() + " " + getGroup() + " " + match + " " + teamOneName + " " + teamOneImage + " " + teamTwoName + " " + teamTwoImage;
    }
}
