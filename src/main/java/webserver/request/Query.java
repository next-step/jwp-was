package webserver.request;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Query {

    private final Map<String, String> queryMap;

    public Query(Map<String, String> queryMap) {
        this.queryMap = queryMap;
    }

    public static Query from(String queryStr) {
        Map<String, String> queryMap = Arrays.stream(queryStr.split("&"))
                .map(querySplit -> {
                    String[] queryKV = querySplit.split("=");
                    return Map.entry(queryKV[0], queryKV[1]);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new Query(queryMap);
    }

    public Map<String, String> getQueryMap() {
        return queryMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Query)) return false;
        Query query = (Query) o;
        return Objects.equals(getQueryMap(), query.getQueryMap());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQueryMap());
    }
}
