package cookie;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CookieManager {

    private static final String SEPERATOR = "; ";
    private static final String SEPERATOR_VALUE = "=";

    public static Cookies read(final String cookiesString) {
        if (cookiesString == null) return new Cookies();

        String[] split = cookiesString.split(SEPERATOR);
        Map<String, String> cookieMap = Arrays.stream(split)
                .map(e -> e.split(SEPERATOR_VALUE))
                .collect(Collectors
                        .toMap(e -> e[0], e -> e[1]));

        return new Cookies(cookieMap);
    }
}
