package webserver.http.request;

public class RequestBody {

    private QueryParameter queryParameter;

    private RequestBody(QueryParameter parameter) {
        this.queryParameter = parameter;
    }

    public static RequestBody parse(String value) {
        return new RequestBody(QueryParameter.parse(value));
    }

    public String getValue(String key) {
        return queryParameter.getParameter(key);
    }
}
