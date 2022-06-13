package webserver.response;

public class Response {
    private final ResponseHeader responseHeader;
    private final ResponseBody responseBody;

    public Response(ResponseHeader responseHeader, ResponseBody responseBody) {
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return String.format(
                "%s\r\n%s",
                responseHeader.setContentLength(responseBody.getBytesLength()),
                responseBody
        );
    }
}
