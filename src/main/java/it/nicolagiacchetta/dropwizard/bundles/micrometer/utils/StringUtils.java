package it.nicolagiacchetta.dropwizard.bundles.micrometer.utils;

public class StringUtils {

    public static boolean isNullOrEmpty(String s) {
        return (s == null || s.trim().isEmpty());
    }

}
