package request;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import utils.QueryParse;

public class Cookie {
    private String logined;

    public Cookie(String logined) {
        this.logined = logined;
    }

    public String getLogined() {
        return logined;
    }

    public static Cookie of(String header) {
        String[] headerSplit = QueryParse.parse(header);

        Map<String, String> map = Arrays.stream(headerSplit)
            .map(QueryParse::parseToMap)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new Cookie(map.get("logined"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cookie cookie = (Cookie) o;
        return Objects.equals(logined, cookie.logined);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logined);
    }
}
