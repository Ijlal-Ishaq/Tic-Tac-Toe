package com.example.tictactoe;

import android.os.Parcel;
import android.os.Parcelable;

public class game_obj implements Parcelable {

    private String player1;
    private String player2;
    private String game_id;
    private String p_1;
    private String p_2;
    private String p_3;
    private String p_4;
    private String p_5;
    private String p_6;
    private String p_7;
    private String p_8;
    private String p_9;
    private String turn;
    private String win;


    public game_obj(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.game_id=player1+player2;
        p_1=p_2=p_3=p_4=p_5=p_6=p_7=p_8=p_9=" ";
        this.turn="O";
        this.win="";

    }


    protected game_obj(Parcel in) {
        player1 = in.readString();
        player2 = in.readString();
        game_id = in.readString();
        p_1 = in.readString();
        p_2 = in.readString();
        p_3 = in.readString();
        p_4 = in.readString();
        p_5 = in.readString();
        p_6 = in.readString();
        p_7 = in.readString();
        p_8 = in.readString();
        p_9 = in.readString();
        turn = in.readString();
        win=in.readString();
    }

    public static final Creator<game_obj> CREATOR = new Creator<game_obj>() {
        @Override
        public game_obj createFromParcel(Parcel in) {
            return new game_obj(in);
        }

        @Override
        public game_obj[] newArray(int size) {
            return new game_obj[size];
        }
    };

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getP_1() {
        return p_1;
    }

    public void setP_1(String p_1) {
        this.p_1 = p_1;
    }

    public String getP_2() {
        return p_2;
    }

    public void setP_2(String p_2) {
        this.p_2 = p_2;
    }

    public String getP_3() {
        return p_3;
    }

    public void setP_3(String p_3) {
        this.p_3 = p_3;
    }

    public String getP_4() {
        return p_4;
    }

    public void setP_4(String p_4) {
        this.p_4 = p_4;
    }

    public String getP_5() {
        return p_5;
    }

    public void setP_5(String p_5) {
        this.p_5 = p_5;
    }

    public String getP_6() {
        return p_6;
    }

    public void setP_6(String p_6) {
        this.p_6 = p_6;
    }

    public String getP_7() {
        return p_7;
    }

    public void setP_7(String p_7) {
        this.p_7 = p_7;
    }

    public String getP_8() {
        return p_8;
    }

    public void setP_8(String p_8) {
        this.p_8 = p_8;
    }

    public String getP_9() {
        return p_9;
    }

    public void setP_9(String p_9) {
        this.p_9 = p_9;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(player1);
        parcel.writeString(player2);
        parcel.writeString(game_id);
        parcel.writeString(p_1);
        parcel.writeString(p_2);
        parcel.writeString(p_3);
        parcel.writeString(p_4);
        parcel.writeString(p_5);
        parcel.writeString(p_6);
        parcel.writeString(p_7);
        parcel.writeString(p_8);
        parcel.writeString(p_9);
        parcel.writeString(turn);
        parcel.writeString(win);
    }
}
