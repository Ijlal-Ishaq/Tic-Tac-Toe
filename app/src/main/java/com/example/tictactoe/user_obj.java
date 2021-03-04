package com.example.tictactoe;

import java.util.List;

public class user_obj {

    private String username;
    private String id;
    private List<String> friends;
    private int win;
    private int loss;
    private int draw;


    public user_obj(String username, String id, List<String> friends, int win, int loss, int draw) {
        this.username = username;
        this.id = id;
        this.friends = friends;
        this.win = win;
        this.loss = loss;
        this.draw = draw;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }
}
