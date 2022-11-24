package de.atslega.jtttc;

import java.util.Scanner;

import static de.atslega.jtttc.Main.*;

public class Game {

    GameMap gameMap;
    Scanner scanner;
    char[] alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    Player player1;
    Player player2;

    public Game(GameMap gameMap, Scanner scanner) {
        this.gameMap = gameMap;
        this.scanner = scanner;

        this.player1 = gameMap.getPlayer1();
        this.player2 = gameMap.getPlayer2();
    }

    //print the map and show the score and the current round
    public void printMap() {
        System.out.print("     ");
        for (int i = 0; i < gameMap.getGameMap()[0].length; i++) {

            System.out.print(alphabetString[i] + "   ");
        }
        System.out.print("\n");

        printSeparator();
        System.out.print("\n");

        for (int i = 0; i < gameMap.getGameMap().length; i++) {
            if (i + 1 > 9) {
                System.out.print(i + 1 + " |");
            } else {
                System.out.print(i + 1 + "  |");
            }
            for (int j = 0; j < gameMap.getGameMap()[i].length; j++) {
                switch (gameMap.getGameMap()[i][j]) {
                    case 0 -> System.out.print(" - |");
                    case 1 -> System.out.print(" x |");
                    case 2 -> System.out.print(" o |");
                }
            }
            switch (i) {
                case 0 -> System.out.print("   "+gameMap.getPlayer1().getName()+" Score: " + gameMap.getPlayer1().getScore());
                case 1 -> System.out.print("   "+gameMap.getPlayer2().getName()+" Score: " + gameMap.getPlayer2().getScore());
                case 3 -> {
                    if (gameMap.getGameMap().length > 3) System.out.print("   Round: " + gameMap.getNowRound());
                }
            }
            System.out.print("\n");
        }

        printSeparator();
        if (gameMap.getGameMap().length < 4) {
            System.out.print("   Round: 1\n");
        }

    }

    private void printSeparator() {
        System.out.print("   |");

        for (int i = 0; i < gameMap.getGameMap().length; i++) {
            System.out.print("---");

            if (i != gameMap.getGameMap().length - 1) {
                System.out.print("-");
            }
        }
        System.out.print("|");
    }

    //get Score
    public Character getWinner() {
        String[] vertical = new String[gameMap.getGameMap().length];
        String[] horizontal = new String[gameMap.getGameMap().length];
        String[] diagonally1 = new String[gameMap.getGameMap().length];
        String[] diagonally2 = new String[gameMap.getGameMap().length];

        for (int i = 0; i < gameMap.getGameMap().length; i++) {
            StringBuilder horizontalString = new StringBuilder();
            for (int j = 0; j < gameMap.getGameMap().length; j++) {
                int x = gameMap.getGameMap().length-1-j;

                horizontalString.append(gameMap.getGameMap()[i][j]);

                if (vertical[j] != null) {
                    vertical[j] = vertical[j] + gameMap.getGameMap()[i][j];
                } else {
                    vertical[j] = String.valueOf(gameMap.getGameMap()[i][j]);
                }

                try {
                    if (diagonally1[i] != null) {
                        diagonally1[i] = diagonally1[i] + gameMap.getGameMap()[j][i+j];
                    } else {
                        diagonally1[i] = String.valueOf(gameMap.getGameMap()[j][i+j]);
                    }
                } catch (Exception ignored) {}

                try {
                    if (diagonally2[i] != null) {
                        diagonally2[i] = diagonally2[i] + gameMap.getGameMap()[x][i-x];
                    } else {
                        diagonally2[i] = String.valueOf(gameMap.getGameMap()[x][i-x]);
                    }
                } catch (Exception ignored) {}
            }
            horizontal[i] = horizontalString.toString();
        }

        for (int i = 0; i < gameMap.getGameMap().length; i++) {
            if(diagonally1[i] != null && diagonally1[i].contains("111")) return Character.X_CHARACTER;
            if(diagonally1[i] != null && diagonally1[i].contains("222")) return Character.O_CHARACTER;

            if(diagonally2[i] != null && diagonally2[i].contains("111")) return Character.X_CHARACTER;
            if(diagonally2[i] != null && diagonally2[i].contains("222")) return Character.O_CHARACTER;

            if(horizontal[i] != null && horizontal[i].contains("111")) return Character.X_CHARACTER;
            if(horizontal[i] != null && horizontal[i].contains("222")) return Character.O_CHARACTER;

            if(vertical[i] != null && vertical[i].contains("111")) return Character.X_CHARACTER;
            if(vertical[i] != null && vertical[i].contains("222")) return Character.O_CHARACTER;
        }


        return null;
    }

    public boolean playField(Player player) {
        boolean wrongInput = false;
        boolean skip = false;

        int column = -1;
        int row = -1;

        do {
            wrongInput = false;
            ;

            System.out.print("\n" + GAP + "'" + player.getName() + "' wähle ein Feld: ");

            String field = scanner.nextLine();
            String[] fieldSplit = field.split("");

            if (fieldSplit.length == 2) {
                for (int i = 0; i < alphabetString.length; i++) {
                    if (java.lang.Character.toString(alphabetString[i]).equals(fieldSplit[0].toUpperCase())) {
                        column = i;
                    }
                }
                if (column == -1 || (column + 1) > gameMap.getGameMap().length) {
                    wrongInput = true;
                    System.out.print(GAP + "Das Feld existiert nicht.");
                } else {
                    try {
                        row = Integer.parseInt(fieldSplit[1]);

                        if (row > gameMap.getGameMap().length) {
                            System.out.print(GAP + "Das Feld existiert nicht.");
                            wrongInput = true;
                        }
                        row--;
                    } catch (Exception ex) {
                        System.out.print(GAP + "Das Feld existiert nicht.");
                        wrongInput = true;
                    }

                    if (!gameMap.isFieldFree(column, row)) {
                        System.out.print(GAP + "Das Feld ist bereits belegt.");
                        wrongInput = true;
                    }
                }
            } else if (fieldSplit.length == 4){
                if (field.toUpperCase().equals("SKIP")){
                    skip = true;
                }
            } else {
                System.out.print(GAP + "Das Feld existiert nicht.");
                wrongInput = true;
            }
        } while (wrongInput);

        if (!skip){
            gameMap.setField(player, column, row);

            clearConsole();
            printMap();

            return true;
        }

        return false;
    }

    public void playRound(int currentRound, int roundsNumber){
        gameMap.setNowRound(currentRound);
        printMap();

        int mapHeight = gameMap.getGameMap().length;
        int mapWeight = gameMap.getGameMap()[0].length;

        Player winner = null;

        int count = 0;
        boolean indecisive = false;

        do {
            boolean keepOn = playField(player1);
            count++;

            if (count != mapWeight * mapHeight && keepOn) {
                Character winner1Character = getWinner();
                if (winner1Character != null) {
                    winner = winner1Character == player1.getCharacter() ? player1 : player2;
                } else {
                    boolean keepOn2 = playField(player2);
                    count++;

                    if (count != mapWeight * mapHeight && keepOn2) {
                        Character winner2Character = getWinner();
                        if (winner2Character != null) {
                            winner = winner2Character == player1.getCharacter() ? player1 : player2;
                        }
                    } else {
                        indecisive = true;
                    }

                }
            } else {
                indecisive = true;
            }
        } while (winner == null && !indecisive);

        if (!indecisive) {
            System.out.println("\n" + GAP + "Der Gewinner ist '" + (winner.getName()) + "'");
            winner.setScore(winner.getScore() + 1);
        } else {
            System.out.println("\n" + GAP + "Die Runde ist unentschieden. Beide bekommen ein Punkt.");
            player1.setScore(player1.getScore() + 1);
            player2.setScore(player2.getScore() + 1);
        }

        gameMap.clearGameMap();

        clearConsole();

        if (currentRound != roundsNumber) {
            runCountdown("Nächste Runde startet in: ");
            clearConsole();
        } else {
            if (player1.getScore() == player2.getScore()){
                System.out.println("Das Spiel ist vorbei! Es ist unentschieden.");
            } else {
                if (player1.getScore() > player2.getScore()) {
                    System.out.println("Das Spiel ist vorbei! '"+player1.getName()+"' hat das Spiel gewonnen.");
                } else {
                    System.out.println("Das Spiel ist vorbei! '"+player2.getName()+"' hat das Spiel gewonnen.");
                }

            }

        }
    }

}
