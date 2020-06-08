package http;

import org.apache.logging.log4j.util.Strings;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryStringParser {
    public static QueryString parse(final String values) {
        if (values == null) {
            throw new InvalidParameterException();
        }

        String[] params = values.split("&");

        Map<String, String> map = map = Arrays.stream(params)
                .filter(p -> p.contains("="))
                .map(p -> p.split("="))
                .collect(Collectors
                        .toMap(e -> e[0], e -> (e.length > 1 ? decode(e[1]) : Strings.EMPTY)));
        return new QueryString(map);
    }

    private static String decode(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
