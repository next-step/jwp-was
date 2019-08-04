package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static String COOKIE_DELIMETER = ";";

    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> queryParameters = new HashMap<>();

        if (StringUtils.isBlank(queryString)) {
            return queryParameters;
        }

        String[] parameters = queryString.split(RequestURL.QUERY_DELIMETER);

        for (String parameter : parameters) {
            String[] keyValuePair = parameter.split(RequestURL.KEY_VALUE_DELIMETER);
            if (keyValuePair.length == 2) {
                queryParameters.put(keyValuePair[0], keyValuePair[1]);  // 중복된 키 값에 대해선 마지막 값만 유지
            }
        }

        return queryParameters;
    }

    public static Map<String, Cookie> parserCookie(String string) {
        Map<String, Cookie> cookies = new HashMap<>();

        if (StringUtils.isBlank(string)) {
            return cookies;
        }

        String[] splitCookies = string.split(COOKIE_DELIMETER);

        for (String cookie : splitCookies) {
            String[] keyValuePair = cookie.split(RequestURL.KEY_VALUE_DELIMETER);
            if (keyValuePair.length == 2) {
                cookies.put(keyValuePair[0].trim(), new Cookie(keyValuePair[0], keyValuePair[1]));
            }
        }

        return cookies;
    }
}
