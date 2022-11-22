package de.atslega.jtttc;

public class Game {

    GameMap gameMap;
    char[] alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public Game(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    //make map and show score
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

    //create play field
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
            }
            horizontal[i] = horizontalString.toString();
        }

        for (int x = 0; x < gameMap.getGameMap().length; x++) {
            for (int i = gameMap.getGameMap().length-1; i >= 0; i--) {
                try {
                    if (diagonally2[x] != null) {
                        diagonally2[x] = diagonally2[x] + gameMap.getGameMap()[i][x-i];
                    } else {
                        diagonally2[x] = String.valueOf(gameMap.getGameMap()[i][x-i]);
                    }
                } catch (Exception ignored) {}
            }
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

}
