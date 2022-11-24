package de.atslega.jtttc;

public class Player {
    private final String name;
    private final Character character;
    private int score;

    public Player(Character character, int points, String name) {
        this.character = character;
        this.score = points;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Character getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }
}
