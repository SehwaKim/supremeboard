package me.sehwa.supremeboard.util;

public class StringUtils {

    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        if (str.isEmpty()) {
            return true;
        }
        return false;
    }

    private StringUtils(){}
}
