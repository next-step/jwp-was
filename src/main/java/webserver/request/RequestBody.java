package webserver.request;

import webserver.request.exception.IllegalQueryStringException;
import webserver.request.exception.IllegalQueryStringKeyException;
import webserver.request.exception.IllegalRequestBodyException;
import webserver.request.exception.IllegalRequestBodyKeyException;

public class RequestBody {
    private final QueryString queryString;

    private RequestBody(QueryString queryString) {
        this.queryString = queryString;
    }

    public static RequestBody from(String body) {
        try {
            return new RequestBody(QueryString.from(body));
        } catch (IllegalQueryStringException exception) {
            throw new IllegalRequestBodyException(body);
        }

    }

    public String get(String key) {
        try {
            return queryString.get(key);
        } catch (IllegalQueryStringKeyException exception) {
            throw new IllegalRequestBodyKeyException(key);
        }
    }
}
