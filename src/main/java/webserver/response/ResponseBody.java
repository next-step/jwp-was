package webserver.response;

public class ResponseBody {

    private String body;

    public ResponseBody(String body) {
        this.body = body;
    }

    public static ResponseBody parse(String value) {
        return new ResponseBody(value);
    }

    public String getBody() {
        return body;
    }
}
