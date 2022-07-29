package webserver.http.cookie.parser;

import webserver.http.cookie.Cookie;
import webserver.http.cookie.Cookies;
import webserver.http.request.KeyValuePair;
import webserver.http.request.parser.KeyValuePairParser;

import java.util.Arrays;

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
                                toMap(KeyValuePair::getKey, pair ->
                                        new Cookie(pair.getKey(), pair.getValue())),
                                Cookies::new
                        )
                );

    }
}
