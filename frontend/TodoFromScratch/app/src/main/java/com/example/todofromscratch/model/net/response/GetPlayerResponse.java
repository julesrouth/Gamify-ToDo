package com.example.todofromscratch.model.net.response;

import com.example.todofromscratch.model.domain.Player;

public class GetPlayerResponse extends Response{

    Player player;

    public GetPlayerResponse(Player player, String message) {
        super(true, null);
        this.player = player;
    }

    public GetPlayerResponse(String message) {
        super(false, message);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
