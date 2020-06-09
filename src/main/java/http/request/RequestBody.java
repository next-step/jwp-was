package http.request;

public class RequestBody {

    private String body;
    public QueryString queryString;

    public RequestBody(String body) {
        this.body = body;
        this.queryString = new QueryString(body);
    }

    public String getBody() {
        return body;
    }

    public String getParameter(String key) {
        return queryString.getParameter(key);
    }
}
