package de.atslega.jtttc.dto;

public class GameMap {
    private int MAP_W = 3;
    private int MAP_H = 3;
    private int nowRound;

    private final Player player1;
    private final Player player2;

    private int[][] gameMap;

    public GameMap(int map_w, int map_h, Player player1, Player player2) {
        this.MAP_H = map_h;
        this.MAP_W = map_w;

        this.player1 = player1;
        this.player2 = player2;

        gameMap = new int[map_h][map_h];
        nowRound = 1;
    }

    public GameMap(Player player1, Player player2) {
        gameMap = new int[MAP_W][MAP_H];
        nowRound = 1;

        this.player1 = player1;
        this.player2 = player2;
    }

    public int[][] getGameMap() {
        return gameMap;
    }

    public boolean isFieldFree(int column, int row) {
        return gameMap[row][column] == 0;
    }

    public void setField(Player player, int column, int row) {
        gameMap[row][column] = player.getCharacter().getCharacterNumber();
    }

    public void clearGameMap() {
        gameMap = new int[MAP_H][MAP_W];
    }

    public int getNowRound() {
        return nowRound;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setNowRound(int nowRound) {
        this.nowRound = nowRound;
    }
}
