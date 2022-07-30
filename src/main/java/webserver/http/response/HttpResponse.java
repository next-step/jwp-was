package webserver.http.response;

import java.util.Optional;

public class HttpResponse {

    private final ResponseLine responseLine;
    private final ResponseHeader responseHeader;
    private final Optional<ResponseBody> responseBody;

    public HttpResponse(ResponseLine responseLine, ResponseHeader responseHeader, Optional<ResponseBody> responseBody) {
        this.responseLine = responseLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }

    public static HttpResponse redirect(final String redirectUrl){
        return new HttpResponse(
                ResponseLine.of302(),
                ResponseHeader.of302(redirectUrl),
                Optional.empty()
        );
    }

    public ResponseLine getResponseLine() {
        return responseLine;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public Optional<ResponseBody> getResponseBody() {
        return responseBody;
    }
}
