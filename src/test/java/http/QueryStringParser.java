package http;

import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryStringParser {
    public static QueryString parse(String s) {
        String[] params = s.split("&");

        Map<String, String> map = map = Arrays.stream(params)
                .filter(p -> p.contains("="))
                .map(p -> p.split("="))
                .collect(Collectors
                        .toMap(e -> e[0], e -> (e.length > 1 ? e[1] : Strings.EMPTY)));
        return new QueryString(map);
    }
}