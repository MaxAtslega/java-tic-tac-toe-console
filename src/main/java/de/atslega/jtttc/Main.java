package de.atslega.jtttc;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {
    static char[] alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    static String GAP = "   ";
    private static Scanner scanner;
    public static final String[] languages = new String[]{"de", "en"};
    public static GameMap gameMap;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        printHeadline();

        int mapSize = askMapSize();
        int roundsNumber = askRoundNumber();

        String player1Name = askPlayerName(1);
        String player2Name = askPlayerName(2);

        Character player1Character = askPlayer1Character();
        Character player2Character = player1Character == Character.X_CHARACTER ? Character.O_CHARACTER : Character.X_CHARACTER;

        Player player1 = new Player(player1Character, 0, player1Name);
        Player player2 = new Player(player2Character, 0, player2Name);

        System.out.println();

        System.out.println("Spieler " + player1.getName() + " hat: " + (player1Character.getCharacterIcon()));
        System.out.println("Spieler " + player2.getName() + " hat: " + (player2Character.getCharacterIcon()));

        System.out.println();

        System.out.print("Spiel startet in: ");
        runCountdown();

        clearConsole();

        gameMap = new GameMap(mapSize, mapSize, player1, player2);

        for (int i = 1; i <= roundsNumber; i++) {
            gameMap.setNowRound(i);

            Game game = new Game(gameMap);
            game.printMap();

            Player winner = null;

            int count = 0;
            boolean indecisive = false;

            do {
                playField(player1, game);
                count++;

                if (count != mapSize * mapSize) {
                    Character winner1Character = game.getWinner();
                    if (winner1Character != null) {
                        winner = winner1Character == player1.getCharacter() ? player1 : player2;
                    } else {
                        playField(player2, game);
                        count++;

                        if (count != mapSize * mapSize) {
                            Character winner2Character = game.getWinner();
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

            if (i != roundsNumber) {
                System.out.print("Nächste Runde startet in: ");
                runCountdown();
                clearConsole();
            } else {
                if (player1.getScore() == player2.getScore()){
                    System.out.print("Das Spiel ist vorbei! Es ist unentschieden.");
                } else {
                    if (player1.getScore() > player2.getScore()) {
                        System.out.print("Das Spiel ist vorbei! '"+player1.getName()+"' hat das Spiel gewonnen.");
                    } else {
                        System.out.print("Das Spiel ist vorbei! '"+player2.getName()+"' hat das Spiel gewonnen.");
                    }

                }

            }

        }
    }

    private static void getWinnerOfGame(Player player1, Player player2){

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

    private static String convertStringArrayToString(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
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

    public static Character askPlayer1Character() {
        String character;
        do {
            System.out.print("Spieler 1: Wähle deine Figure zwischen 'o' und 'x' (Standard: 'o'): ");
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

    public static void runCountdown() {
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

    public static void playField(Player player, Game game) {
        boolean wrongInput = false;

        int column = -1;
        int row = -1;

        do {
            wrongInput = false;
            ;

            System.out.print("\n" + GAP + "Spieler '" + player.getName() + "' wählen Sie ein Feld: ");

            String[] field = scanner.nextLine().split("");

            if (field.length == 2) {
                for (int i = 0; i < alphabetString.length; i++) {
                    if (java.lang.Character.toString(alphabetString[i]).equals(field[0].toUpperCase())) {
                        column = i;
                    }
                }
                if (column == -1 || (column + 1) > gameMap.getGameMap().length) {
                    wrongInput = true;
                    System.out.print(GAP + "Das Feld existiert nicht.");
                } else {
                    try {
                        row = Integer.parseInt(field[1]);

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
            } else {
                System.out.print(GAP + "Das Feld existiert nicht.");
                wrongInput = true;
            }
        } while (wrongInput);

        gameMap.setField(player, column, row);

        clearConsole();
        game.printMap();
    }

}
