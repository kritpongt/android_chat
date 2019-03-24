package com.example.androidtabletop.Model;

public class Bookmarks {
    private String GameId;
    private String GameName;
    private String GameDescription;
    private String GameImage;
    private String GameDate;

    public Bookmarks() {
    }

    public Bookmarks(String gameId, String gameName, String gameDescription, String gameImage, String gameDate) {
        GameId = gameId;
        GameName = gameName;
        GameDescription = gameDescription;
        GameImage = gameImage;
        GameDate = gameDate;
    }

    public String getGameDate() {
        return GameDate;
    }

    public void setGameDate(String gameDate) {
        GameDate = gameDate;
    }

    public String getGameId() {
        return GameId;
    }

    public void setGameId(String gameId) {
        GameId = gameId;
    }

    public String getGameName() {
        return GameName;
    }

    public void setGameName(String gameName) {
        GameName = gameName;
    }

    public String getGameDescription() {
        return GameDescription;
    }

    public void setGameDescription(String gameDescription) {
        GameDescription = gameDescription;
    }

    public String getGameImage() {
        return GameImage;
    }

    public void setGameImage(String gameImage) {
        GameImage = gameImage;
    }
}
