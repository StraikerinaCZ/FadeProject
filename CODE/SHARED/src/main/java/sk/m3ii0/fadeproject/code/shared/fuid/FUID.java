package sk.m3ii0.fadeproject.code.shared.fuid;

import java.util.Random;

public class FUID {

    public static FUID getByExisting(String id) {
        return new FUID(id);
    }

    public static FUID getNew() {

        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        char[] possibleCharacters = new char[] {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'
        };

        for (int var = 0; var <= 32; var++) {
            builder.append(possibleCharacters[random.nextInt(61)]);
        }

        return new FUID(builder.toString());
    }

    private final String id;

    private FUID(String id) {
        this.id = id;
    }

    public String get() {
        return id;
    }

}