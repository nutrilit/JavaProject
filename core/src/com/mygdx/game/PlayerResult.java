package com.mygdx.game;
import java.util.Collections;
import java.util.Comparator;
public class PlayerResult implements Comparable<PlayerResult> {
    private String playerName;
    private int score;

    public PlayerResult(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(PlayerResult other) {
        // Sortowanie malejące
        return Integer.compare(other.getScore(), this.score);
    }
}
