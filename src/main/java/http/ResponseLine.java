package http;

public class ResponseLine {

    private static final String HTTP_PROTOCOL = "HTTP/1.1";
    private static final String BLANK = " ";
    private final String code;
    private final String message;

    public ResponseLine(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getResponseLine() {
        return HTTP_PROTOCOL + BLANK + code + BLANK + message;
    }

    @Override
    public String toString() {
        return "ResponseLine{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
