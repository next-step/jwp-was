package http.httprequest.requestline;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import utils.QueryStringParser;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class RequestParams {

    private final Map<String, String> params;

    public RequestParams(Map<String, String> params) {
        this.params = params;
    }

    public String get(String key) {
        return params.get(key);
    }

    public static RequestParams from(String queryString) {
        if (StringUtils.isEmpty(queryString)) {
            return new RequestParams(Collections.emptyMap());
        }

        Map<String, String> params = QueryStringParser.parse(queryString);

        return new RequestParams(params);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestParams requestParams1 = (RequestParams) o;
        return params.equals(requestParams1.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(params);
    }

    @Override
    public String toString() {
        return "Params{" +
                "params=" + params +
                '}';
    }
}
