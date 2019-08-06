package webserver.request;

import java.util.List;

/**
 * Created by hspark on 2019-08-05.
 */
public class RequestBody {
    private RequestParameters requestParameters;

    private RequestBody(RequestParameters requestParameters) {
        this.requestParameters = requestParameters;
    }

    public static RequestBody parse(String rawBody) {
        return new RequestBody(RequestParameters.parse(rawBody));
    }

    public String getOne(String name) {
        return requestParameters.getOne(name);
    }

    public List<String> getAll(String name) {
        return requestParameters.getAll(name);
    }
}
