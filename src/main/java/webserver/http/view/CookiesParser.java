package webserver.http.view;

import webserver.http.domain.Cookie;
import webserver.http.domain.Cookies;
import webserver.http.domain.KeyValuePair;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class CookiesParser {
    public static final String EACH_COOKIE_DELIMITER_REGEX = "; ";
    public static final String COOKIE_KEY_VALUE_DELIMITER = "=";
    private final KeyValuePairParser keyValuePairParser;

    public CookiesParser(KeyValuePairParser keyValuePairParser) {
        this.keyValuePairParser = keyValuePairParser;
    }

    public Cookies parse(String message) {
        return Arrays.stream(message.split(EACH_COOKIE_DELIMITER_REGEX))
                .map(cookie -> keyValuePairParser.parse(cookie, COOKIE_KEY_VALUE_DELIMITER, false))
                .collect(collectingAndThen(
                                toMap(
                                        KeyValuePair::getKey, pair ->
                                        new Cookie(pair.getKey(), pair.getValue()),
                                        (oldCookie, newCookie) -> newCookie,
                                        LinkedHashMap::new
                                ),
                                Cookies::new
                        )
                );

    }
}
