package de.atslega.jtttc.dto;

public class Player {
    private final String name;

    private int score;
    private final Character character;

    public Player(Character character, int points, String name){
        this.character = character;
        this.score = points;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public Character getCharacter() {
        return character;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }
}
