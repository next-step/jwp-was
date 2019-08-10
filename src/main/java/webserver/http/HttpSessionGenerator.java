package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.UUID;

public class HttpSessionGenerator {
    private static final String UUID_SEPARATOR = "-";

    public static String generate() {
        return UUID.randomUUID()
                .toString()
                .replaceAll(UUID_SEPARATOR, StringUtils.EMPTY);
    }
}
