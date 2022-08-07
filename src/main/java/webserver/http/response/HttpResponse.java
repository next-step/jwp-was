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

    public static HttpResponse getStaticFile(final ContentType contentType, final String path) {
        ResponseHeader responseHeader = ResponseHeader.baseResponseHeader();
        responseHeader.setContentType(contentType);

        return new HttpResponse(
                ResponseLine.of200(),
                responseHeader,
                Optional.of(ResponseBody.fromFile(path))
        );
    }

    public static HttpResponse getView(final String viewPath) {
        return new HttpResponse(
                ResponseLine.of200(),
                ResponseHeader.baseResponseHeader(),
                Optional.of(ResponseBody.fromFile(viewPath))
        );
    }

    public static HttpResponse getDynamicView(final String template){
        return new HttpResponse(
                ResponseLine.of200(),
                ResponseHeader.baseResponseHeader(),
                Optional.of(ResponseBody.fromDynamicView(template))
        );
    }

    public static HttpResponse redirect(final String redirectUrl){
        ResponseHeader responseHeader = ResponseHeader.baseResponseHeader();
        responseHeader.setLocation(redirectUrl);

        return new HttpResponse(
                ResponseLine.of302(),
                responseHeader,
                Optional.empty()
        );
    }

    public static HttpResponse notFound() {
        return new HttpResponse(
                ResponseLine.of404(),
                ResponseHeader.baseResponseHeader(),
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
