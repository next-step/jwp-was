package webserver.domain;

public class RequestBody {
    private String value;

    public RequestBody(){}

    public RequestBody(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
