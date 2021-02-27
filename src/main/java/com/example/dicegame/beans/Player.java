package com.example.dicegame.beans;

import com.example.dicegame.enums.PlayerStatus;

public class Player {

    private int sequenceId;
    private String name;
    private PlayerStatus status;
    private int score;

    public Player() {

    }

    public Player(String name) {
        this.name = name;
        this.status = PlayerStatus.IDLE;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
