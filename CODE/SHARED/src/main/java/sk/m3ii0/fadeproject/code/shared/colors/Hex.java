package sk.m3ii0.fadeproject.code.shared.colors;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hex {

    public static final String PREFIX = "&#faff69| <#faff69>Fade</#f55a22> &#f55a22-> &7";

    private final static Pattern RGB;
    private final static Pattern GRADIENT_1;
    private final static Pattern GRADIENT_2;

    static {

        RGB = Pattern.compile("&#......");
        GRADIENT_1 = Pattern.compile("<#......>");
        GRADIENT_2 = Pattern.compile("</#......>");

    }

    public static String highlight(String text) {
        return "§r<#beff63>" + text + "</#dded2f>§r";
    }

    public static String colorize(String text) {

        Matcher rgbs = RGB.matcher(text);
        Matcher g1s = GRADIENT_1.matcher(text);
        Matcher g2s = GRADIENT_2.matcher(text);

        while (g1s.find() && g2s.find()) {

            String hex1 = g1s.group(0);
            String hex2 = g2s.group(0);

            Color from = Color.decode(hex1.replace("<", "").replace(">", ""));
            Color to = Color.decode(hex2.replace("</", "").replace(">", ""));

            String between = "";

            String[] split = text.split(hex1);

            for (String var : split) {
                if (var.contains(hex2)) {
                    between = var.split(hex2)[0];
                }
            }

            text = text.replaceFirst(hex1 + between + hex2, rgbGradient(between, from, to));

        }

        while (rgbs.find()) {

            String rawText = rgbs.group(0);
            String rgbColor = rawText.replace("&", "");
            Color finalColor = Color.decode(rgbColor);

            text = text.replaceFirst(rawText, ChatColor.of(finalColor) + "");

        }

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private static String rgbGradient(String str, Color from, Color to) {

        final double[] red = linear(from.getRed(), to.getRed(), str.length());
        final double[] green = linear(from.getGreen(), to.getGreen(), str.length());
        final double[] blue = linear(from.getBlue(), to.getBlue(), str.length());
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            builder.append(ChatColor.of(new Color(
                            (int) Math.round(red[i]),
                            (int) Math.round(green[i]),
                            (int) Math.round(blue[i]))))
                    .append(str.charAt(i));
        }

        return builder.toString();
    }

    private static double[] linear(double from, double to, int max) {

        final double[] res = new double[max];

        for (int i = 0; i < max; i++) {
            res[i] = from + i * ((to - from) / (max - 1));
        }

        return res;
    }

}