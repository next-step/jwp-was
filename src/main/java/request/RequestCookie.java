package request;

import constant.HttpCookie;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import utils.QueryParse;

public class RequestCookie {

    private Map<String, String> cookies;

    public RequestCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getParameter(String key) {
        return cookies.get(key);
    }

    public String getSessionId() {
        return cookies.get(HttpCookie.JSESSIONID.getValue());
    }

    public static RequestCookie of(String header) {
        if(Objects.isNull(header)) return new RequestCookie(new HashMap<>());
        String[] headerSplit = QueryParse.parse(header);

        return new RequestCookie(Arrays.stream(headerSplit)
            .map(QueryParse::parseToMap)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestCookie cookie = (RequestCookie) o;
        return Objects.equals(cookies, cookie.cookies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookies);
    }
}
