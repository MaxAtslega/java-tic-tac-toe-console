package de.atslega.jtttc;

import de.atslega.jtttc.utils.Player;

public class Map {

    private final int[][] map;
    public int mapSize = 3;
    char[] alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public Map(int mapSize) {
        this.mapSize = mapSize;
        map = new int[mapSize][mapSize];
    }

    //make map and show score
    public void printMap() {
        System.out.print("     ");
        for (int i = 0; i < map.length; i++) {

            System.out.print(alphabetString[i] + "   ");
        }
        System.out.print("\n");

        printSeparator();
        System.out.print("\n");

        for (int i = 0; i < map.length; i++) {
            if (i + 1 > 9) {
                System.out.print(i + 1 + " |");
            } else {
                System.out.print(i + 1 + "  |");
            }
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    case 0 -> System.out.print(" - |");
                    case 1 -> System.out.print(" x |");
                    case 2 -> System.out.print(" o |");
                }
            }
            switch (i) {
                case 0 -> System.out.print("   Player1 Score: 0");
                case 1 -> System.out.print("   Player2 Score: 0");
                case 3 -> {
                    if (map.length > 3) System.out.print("   Round: 0");
                }
            }
            System.out.print("\n");
        }

        printSeparator();
        if (map.length < 4) {
            System.out.print("   Round: 1\n");
        }

    }

    public boolean isFieldFree(int column, int row) {
        return map[row][column] == 0;
    }

    //new field
    public void setField(int player, int column, int row) {
        map[row][column] = player;

        clearConsole();
        printMap();
    }

    //create play field
    private void printSeparator() {
        System.out.print("   |");

        for (int i = 0; i < map.length; i++) {
            System.out.print("---");

            if (i != map.length - 1) {
                System.out.print("-");
            }
        }
        System.out.print("|");
    }

    //clear the current output
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

    //get Score
    public int getWinner() {
        String[] vertical = new String[map.length];
        String[] horizontal = new String[map.length];
        String[] diagonally1 = new String[map.length];
        String[] diagonally2 = new String[map.length];

        for (int i = 0; i < map.length; i++) {
            StringBuilder horizontalString = new StringBuilder();
            for (int j = 0; j < map.length; j++) {
                horizontalString.append(map[i][j]);
            }
            horizontal[i] = horizontalString.toString();
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (vertical[j] != null) {
                    vertical[j] = vertical[j] + map[i][j];
                } else {
                    vertical[j] = String.valueOf(map[i][j]);
                }
            }
        }
        for (int x = 0; x < map.length; x++) {
            for (int i = 0; i < map.length; i++) {
                try {
                    if (diagonally1[x] != null) {
                        diagonally1[x] = diagonally1[x] + map[i][x+i];
                    } else {
                        diagonally1[x] = String.valueOf(map[i][x+i]);
                    }
                } catch (Exception ignored) {}
                //System.out.println(i+"." + (x + i));
            }
        }

        for (int x = 0; x < map.length; x++) {
            for (int i = map.length-1; i >= 0; i--) {
                try {
                    if (diagonally2[x] != null) {
                        diagonally2[x] = diagonally2[x] + map[i][x-i];
                    } else {
                        diagonally2[x] = String.valueOf(map[i][x-i]);
                    }
                } catch (Exception ignored) {}
            }
        }


        for (int i = 0; i < map.length; i++) {
            if(diagonally1[i] != null && diagonally1[i].contains("111")) return 1;
            if(diagonally1[i] != null && diagonally1[i].contains("222")) return 2;

            if(diagonally2[i] != null && diagonally2[i].contains("111")) return 1;
            if(diagonally2[i] != null && diagonally2[i].contains("222")) return 2;

            if(horizontal[i] != null && horizontal[i].contains("111")) return 1;
            if(horizontal[i] != null && horizontal[i].contains("222")) return 2;

            if(vertical[i] != null && vertical[i].contains("111")) return 1;
            if(vertical[i] != null && vertical[i].contains("222")) return 2;
        }


        return 0;
    }

}
