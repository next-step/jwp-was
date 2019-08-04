package webserver.response;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import webserver.request.Headers;
import webserver.request.HttpRequest;

/**
 * Created by hspark on 2019-08-05.
 */
public class HttpResponse {
    public static final String CRLF = "\r\n";
    public static final String CONTENT_TYPE = "Content-Type: ";
    public static final String CONTENT_LENGTH = "Content-Length: ";
    private HttpStatus httpStatus;
    private Headers headers = new Headers();
    private Model model;
    private byte[] body = new byte[0];

    public HttpResponse(HttpStatus httpStatus, byte[] body) {
        this.httpStatus = httpStatus;
        this.body = body;
    }

    public HttpResponse(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public byte[] getBody() {
        return body;
    }

    public String getResponseHeader(HttpRequest httpRequest) {
        StringBuilder stringBuilder = new StringBuilder().append(httpRequest.getRequestLine().getHttpVersion().getHeaderValue())
                .append(StringUtils.SPACE).append(httpStatus.getStatusCode())
                .append(StringUtils.SPACE).append(httpStatus.name()).append(CRLF)
                .append(CONTENT_TYPE)
                .append(httpRequest.getHeaders().getContentTypeByAccept())
                .append(CRLF).append(CONTENT_LENGTH)
                .append(body.length).append(CRLF);

        headers.getHeaders()
                .entrySet()
                .forEach(it -> stringBuilder.append(it.getKey() + ": " + it.getValue()));

        return stringBuilder.append(CRLF).toString();
    }

    public static HttpResponse ok(byte[] body) {
        return new HttpResponse(HttpStatus.OK, body);
    }

    public static HttpResponse redirect(String url) {
        HttpResponse httpResponse = new HttpResponse(HttpStatus.FOUND);
        httpResponse.headers.addLocation(url);
        return httpResponse;
    }

    public static HttpResponse internalServerError(Throwable throwable) {
        return new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage().getBytes());
    }

    public static HttpResponse notFound(Throwable throwable) {
        return new HttpResponse(HttpStatus.NOT_FOUND, throwable.getMessage().getBytes());
    }


}
