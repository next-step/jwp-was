package webserver.http;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.*;
import java.util.stream.Collectors;

public class QueryParam {

    public static final QueryParam EMTPY = new QueryParam(Collections.emptyMap());

    private final Map<String, String> queryParams;

    public QueryParam(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public static QueryParam parse(String queryString) {
        if (queryString == null || queryString.isEmpty()) {
            return QueryParam.EMTPY;
        }

        Map<String, String> queryParams = Arrays.stream(queryString.split("&"))
                .map(array -> array.split("="))
                .collect(Collectors.toMap(v -> v[0], v -> v[1]));

        return new QueryParam(queryParams);
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(queryParams.get(key));
    }

    public List<String> paramValues() {
        return ImmutableList.copyOf(this.queryParams.values());
    }


    public Set<String> paramKeys() {
        return ImmutableSet.copyOf(this.queryParams.keySet());
    }
}
