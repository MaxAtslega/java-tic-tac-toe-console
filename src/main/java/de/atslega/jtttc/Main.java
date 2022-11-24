package de.atslega.jtttc;

import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * TODO
 * - Skip
 * - Kommentare
 */
public class Main {
    public static GameMap gameMap;
    static char[] alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    static String GAP = "   ";
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        printHeadline();

        // ask game settings
        int mapSize = askMapSize();
        int roundsNumber = askRoundNumber();

        String player1Name = askPlayerName(1);
        String player2Name = askPlayerName(2);

        Character player1Character = askPlayer1Character();
        Character player2Character = player1Character == Character.X_CHARACTER ? Character.O_CHARACTER : Character.X_CHARACTER;

        // create players
        Player player1 = new Player(player1Character, 0, player1Name);
        Player player2 = new Player(player2Character, 0, player2Name);

        System.out.println();

        // print player character
        System.out.println("Spieler " + player1.getName() + " hat: " + (player1Character.getCharacterIcon()));
        System.out.println("Spieler " + player2.getName() + " hat: " + (player2Character.getCharacterIcon()));

        System.out.println();

        // start game
        boolean newGame = false;
        do {
            newGame = false;

            runCountdown("Spiel startet in: ");

            clearConsole();

            gameMap = new GameMap(mapSize, mapSize, player1, player2);

            // start rounds
            for (int currentRound = 1; currentRound <= roundsNumber; currentRound++) {
                Game game = new Game(gameMap, scanner);
                game.playRound(currentRound, roundsNumber);
            }
            newGame = askNewGame();
            clearConsole();
        } while (newGame);

    }

    public static void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system

            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void printHeadline() {
        System.out.println("  __________________  __\n" +
                " /_  __/_  __/_  __/ / /\n" +
                "  / /   / /   / /_  / / \n" +
                " / /   / /   / / /_/ /  \n" +
                "/_/   /_/   /_/\\____/   ");
        System.out.println("Spiel von Max Atslega und Felix Menze" + " \n");
    }

    public static int askMapSize() {
        int mapSize = 0;
        do {
            System.out.print("Wie groß soll das Feld sein (Min: 3x3; Max: 26x26) (Standard: 3x3)? ");
            try {
                String mapSizeAsString = scanner.nextLine();

                if (!mapSizeAsString.isEmpty()) {
                    mapSize = Integer.parseInt(mapSizeAsString);
                    if (mapSize > 26 || mapSize < 3) {
                        System.out.println("Das Feld muss mindestens 3x3 und maximal 26x26 groß sein.");
                    }
                } else {
                    mapSize = 3;
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
            System.out.print("Wie viele Runden möchtest du Spielen (Min: 2) (Standard: 3)? ");
            String roundAsString = scanner.nextLine();
            if (!roundAsString.isEmpty()) {
                try {
                    rounds = Integer.parseInt(roundAsString);
                    if (rounds < 2) {
                        System.out.println("Du kannst nicht weniger als 2 Runden spielen.");
                    }
                } catch (Exception ex) {
                    System.out.println("Du kannst nur Nummern eingeben.");
                }
            } else {
                rounds = 2;
            }
        } while (rounds < 2);

        return rounds;
    }

    public static boolean askNewGame() {
        boolean newGame = false;
        boolean wrongInput = false;
        do {
            System.out.print(GAP + "Willst du eine neuen Runde spielen? ('Ja' oder 'Nein') (Standard: 'Ja'): ");
            String answer = scanner.nextLine();

            if (!answer.isEmpty()) {
                answer = answer.toUpperCase();

                if (answer == "JA") {
                    newGame = true;
                } else if (answer != "NEIN") {
                    newGame = false;
                } else {
                    System.out.print(GAP + "Du kannst nur zwischen 'Ja' und 'Nein' auswählen: ");
                    wrongInput = true;
                }
            } else {
                newGame = true;
            }
        } while (wrongInput);

        return newGame;
    }


    public static Character askPlayer1Character() {
        String character;
        do {
            System.out.print("Spieler 1: Wähle deine Figure: 'o' oder 'x' (Standard: 'o'): ");
            character = scanner.nextLine();

            if (!character.isEmpty()) {
                if (Character.fromString(character) == null) {
                    System.out.println("Du kannst nur zwischen 'o' oder 'x' wählen.");
                }
            } else {
                character = "o";
            }

        } while (Character.fromString(character) == null);

        return Character.fromString(character);
    }

    public static String askPlayerName(int number) {
        String name;
        do {
            System.out.print("Spieler " + number + ": Wähle einen Namen: ");
            name = scanner.nextLine();
        } while (name.isEmpty());

        return name;
    }

    public static void runCountdown(String message) {
        System.out.print(message);
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

}
