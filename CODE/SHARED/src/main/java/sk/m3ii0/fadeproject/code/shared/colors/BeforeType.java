package sk.m3ii0.fadeproject.code.shared.colors;

import java.util.ArrayList;
import java.util.List;

public enum BeforeType {

    MIXED("k"),
    BOLD("l"),
    CROSSED("m"),
    UNDERLINED("n"),
    CURSIVE("o");

    BeforeType(String code) {
        this.code = code;
    }

    private final String code;

    public String getCode() {
        return code;
    }

    public static BeforeType[] detect(String text) {

        List<BeforeType> value = new ArrayList<>();

        boolean hasMix = text.contains("&k");
        boolean hasBold = text.contains("&l");
        boolean hasCrossed = text.contains("&m");
        boolean hasUnder = text.contains("&n");
        boolean hasCursive = text.contains("&o");

        if (hasMix) {
            value.add(MIXED);
        }

        if (hasBold) {
            value.add(BOLD);
        }

        if (hasCrossed) {
            value.add(CROSSED);
        }

        if (hasUnder) {
            value.add(UNDERLINED);
        }

        if (hasCursive) {
            value.add(CURSIVE);
        }

        return value.toArray(new BeforeType[0]);
    }

    public static String replaceColors(String text) {
        return text.replace("&k", "").replace("&b", "").replace("&m", "").replace("&n", "").replace("&o", "").replace("&l", "");
    }

}
