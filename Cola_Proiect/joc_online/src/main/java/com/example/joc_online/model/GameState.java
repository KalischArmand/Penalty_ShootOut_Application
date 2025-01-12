package com.example.joc_online.model;


public class GameState {
    private String player;
    private String action;

    public GameState(String player, String action) {
        this.player = player;
        this.action = action;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}