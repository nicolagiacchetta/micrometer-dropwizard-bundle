package it.nicolagiacchetta.dropwizard.bundles.micrometer.utils;

public class DropwizardUtils {

    public static String adaptFormatUrlPattern(String urlPattern) {
        if(StringUtils.isNullOrEmpty(urlPattern))
            return urlPattern;

        StringBuilder builder = new StringBuilder();

        if(!urlPattern.startsWith("/")) {
            builder.append("/");
        }

        builder.append(urlPattern);

        if(!urlPattern.endsWith("/*")) {
            if(!urlPattern.endsWith("/")) {
                builder.append("/");
            }

            builder.append("*");
        }

        return builder.toString();
    }
}
