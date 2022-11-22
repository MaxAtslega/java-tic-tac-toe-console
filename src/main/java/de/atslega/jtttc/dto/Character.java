package de.atslega.jtttc.dto;

public enum Character {
    X_CHARACTER,
    O_CHARACTER;

    public String getCharacterIcon(){
        return switch (this) {
            case O_CHARACTER -> "o";
            case X_CHARACTER -> "x";
        };
    }

    public int getCharacterNumber(){
        return switch (this) {
            case O_CHARACTER -> 2;
            case X_CHARACTER -> 1;
        };
    }

    public static Character fromString(String text) {
        return switch (text) {
            case "o" -> O_CHARACTER;
            case "x"-> X_CHARACTER;
            default -> null;
        };
    }

    public static Character fromNumber(int number) {
        return switch (number) {
            case 1 -> O_CHARACTER;
            case 2 -> X_CHARACTER;
            default -> null;
        };
    }
}
