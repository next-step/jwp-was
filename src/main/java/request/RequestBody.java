package request;

import utils.QueryParse;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestBody {
    private Map<String, String> body;

    public RequestBody(Map<String, String> body) {
        this.body = body;
    }

    public static RequestBody empty() {
        return new RequestBody(Collections.emptyMap());
    }

    public static RequestBody parse(String body) {
        if(body.equals("")) return RequestBody.empty();

        String[] splitArr = QueryParse.values(body);
        return new RequestBody(Arrays.stream(splitArr)
            .map(QueryParse::parseToMap)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public String getParameter(String key) {
        return body.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestBody that = (RequestBody) o;
        return Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }
}
