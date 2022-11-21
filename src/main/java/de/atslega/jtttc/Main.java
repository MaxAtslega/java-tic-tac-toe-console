package de.atslega.jtttc;

import java.util.Scanner;

import static de.atslega.jtttc.utils.Player.O_CHARACTER;
import static de.atslega.jtttc.utils.Player.X_CHARACTER;
import static java.lang.Thread.sleep;

public class Main {
    static char[] alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    static String GAP = "   ";

    private static Scanner scanner;

    public static void main(String[] args) {
        printHeadline();

        scanner = new Scanner(System.in);

        int mapSize = askMapSize();
        int roundsNumber = askRoundNumber();

        int player1Character = askPlayer1Character();
        int player2Character = player1Character == 1 ? O_CHARACTER : X_CHARACTER;

        System.out.println();

        System.out.println("Spieler 1 hat: " + (player1Character == O_CHARACTER ? "o" : "x"));
        System.out.println("Spieler 2 hat: " + (player2Character == O_CHARACTER ? "o" : "x"));

        System.out.println();

        //runCountdown();

        Map.clearConsole();

        Map map = new Map(mapSize);
        map.printMap();

        int winnerOfTTT = 0;

        do {
            playField(map, player1Character);
            int winner = map.getWinner();
            if (winner != 0){
                winnerOfTTT = winner;
            } else {
                playField(map, player2Character);
                int winner2 = map.getWinner();
                if (winner2 != 0){
                    winnerOfTTT = winner2;
                }
            }
        } while (winnerOfTTT == 0);

        System.out.println("\n"+GAP+"Der Gewinner ist Spieler '"+(winnerOfTTT == O_CHARACTER ? "o'" : "x'"));


    }

    public static void printHeadline() {
        System.out.println("  __________________  __\n" +
                " /_  __/_  __/_  __/ / /\n" +
                "  / /   / /   / /_  / / \n" +
                " / /   / /   / / /_/ /  \n" +
                "/_/   /_/   /_/\\____/   ");
        System.out.println("Game by Max Atslega and Felix Menze. \n");
    }

    public static int askMapSize() {
        int mapSize = 0;
        do {
            System.out.print("Wie groß soll das Feld sein (Min: 3x3; Max: 26x26)? ");
            try {
                mapSize = Integer.parseInt(scanner.nextLine());

                if (mapSize > 26 || mapSize < 3) {
                    System.out.println("Das Feld muss mindestens 3x3 und maximal 26x26 groß sein.");
                }
            } catch (Exception ex) {
                System.out.println("Du kannst nur Nummern eingeben.");
            }
        } while (mapSize > 26 || mapSize < 3);

        return mapSize;
    }

    public static int askRoundNumber() {
        int rounds = 0;
        do {
            try {
                System.out.print("Wie viele Runden möchtest du Spielen (Min: 3)? ");
                rounds = Integer.parseInt(scanner.nextLine());

                if (rounds < 3) {
                    System.out.println("Du kannst nicht weniger als 3 Runden spielen.");
                }
            } catch (Exception ex) {
                System.out.println("Du kannst nur Nummern eingeben.");
            }
        } while (rounds < 3);

        return rounds;
    }

    public static int askPlayer1Character() {
        String character;
        do {
            System.out.print("Spieler 1: Wähle deine Figure zwischen 'o' und 'x': ");
            character = scanner.nextLine();

            if (!character.equals("o") && !character.equals("x")) {
                System.out.println("Du kannst nur zwischen 'o' oder 'x' wählen.");
            }
        } while (!character.equals("o") && !character.equals("x"));

        if (character.equals("o")) {
            return O_CHARACTER;
        }

        return X_CHARACTER;
    }

    public static void runCountdown() {
        System.out.print("Spiel startet in: ");
        for (int i = 10; i >= 1; i--) {
            System.out.print(i + " ");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Start! \n");
    }

    public static void playField(Map map, int player) {
        boolean wrongInput = false;

        int column = -1;
        int row = -1;

        do {
            wrongInput = false;
            String playerIcon = player == O_CHARACTER ? "o" : "x";

            System.out.print("\n" + GAP + "Spieler '"+playerIcon+"' wählen Sie ein Feld: ");

            String[] field = scanner.nextLine().split("");

            if (field.length == 2) {
                for (int i = 0; i < alphabetString.length; i++) {
                    if (Character.toString(alphabetString[i]).equals(field[0].toUpperCase())) {
                        column = i;
                    }
                }
                if (column == -1 || (column + 1) > map.mapSize) {
                    wrongInput = true;
                    System.out.print(GAP + "Das Feld existiert nicht.");
                } else {
                    try {
                        row = Integer.parseInt(field[1]);

                        if (row > map.mapSize) {
                            System.out.print(GAP + "Das Feld existiert nicht.");
                            wrongInput = true;
                        }
                        row--;
                    } catch (Exception ex) {
                        System.out.print(GAP + "Das Feld existiert nicht.");
                        wrongInput = true;
                    }

                    if (!map.isFieldFree(column, row)) {
                        System.out.print(GAP + "Das Feld ist bereits belegt.");
                        wrongInput = true;
                    }
                }
            } else {
                System.out.print(GAP + "Das Feld existiert nicht.");
                wrongInput = true;
            }
        } while (wrongInput);

        map.setField(player, column, row);
    }

}
