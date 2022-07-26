package webserver.http.response;

import webserver.http.HttpProtocolSchema;
import webserver.http.HttpVersion;

import static webserver.http.response.HttpResponseMessage.RESPONSE_END_OF_LINE_MARKER;

public class HttpResponseStatusLine {
    private static final String HTTP_RESPONSE_STATUS_LINE_DELIMITER = " ";
    private HttpProtocolSchema httpProtocolSchema;
    private HttpStatus httpStatus;

    public HttpResponseStatusLine(HttpProtocolSchema httpProtocolSchema, HttpStatus httpStatus) {
        this.httpProtocolSchema = httpProtocolSchema;
        this.httpStatus = httpStatus;
    }

    public static HttpResponseStatusLine fromOnePointOne(HttpStatus httpStatus) {
        return new HttpResponseStatusLine(HttpProtocolSchema.of(HttpVersion.ONE_POINT_ONE), httpStatus);
    }

    public String rawStatusLine() {
        return httpProtocolSchema.rawHttpProtocolSchema() + HTTP_RESPONSE_STATUS_LINE_DELIMITER + httpStatus.fullStatusCode() + RESPONSE_END_OF_LINE_MARKER;
    }
}
