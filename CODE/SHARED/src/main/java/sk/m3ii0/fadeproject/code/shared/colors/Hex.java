package sk.m3ii0.fadeproject.code.shared.colors;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hex {

    public static final String PREFIX = "&#faff69| <#faff69>Fade</#f55a22> &#f55a22-> &7";

    public static String highlight(String text) {
        return "§r<#beff63>" + text + "</#dded2f>§r";
    }

    private static final Pattern gradient = Pattern.compile("<(#[A-Za-z0-9]{6})>(.*?)</(#[A-Za-z0-9]{6})>");
    private static final Pattern rgb = Pattern.compile("&#......");

    public static String colorize(String text) {

        Matcher g = gradient.matcher(text);
        Matcher r = rgb.matcher(text);

        while (g.find()) {
            Color start = Color.decode(g.group(0));
            String between = g.group(1);
            Color end = Color.decode(g.group(2));
            text = g.replaceAll(rgbGradient(between, start, end));
        }

        while (r.find()) {
            ChatColor color = ChatColor.of(Color.decode(r.group(0)));
            text = text.replaceFirst(r.group(0), color + "");
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