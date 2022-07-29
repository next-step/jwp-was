package webserver.response;

import static webserver.request.Protocol.HTTP_1_1;
import static webserver.response.ResponseBody.EMPTY_RESPONSE_BODY;
import static webserver.response.StatusCode.FOUND;
import static webserver.response.StatusCode.NOT_FOUND;
import static webserver.response.StatusCode.OK;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import utils.FileIoUtils;
import webserver.request.ContentType;
import webserver.template.HandleBarTemplateLoader;

public class HttpResponse {

    private static final String LOCATION = "Location";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String TEMPLATES = "templates";
    private static final String STATIC = "static";
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String CRLF = "\r\n";
    private static final String COOKIE_FORMAT = "%s=%s; Path=/";
    private static final String HEADER_DELIMITER = ": ";

    private final StatusLine responseLine;
    private final ResponseHeaders responseHeaders;
    private final ResponseBody responseBody;

    public HttpResponse(StatusLine responseLine, ResponseHeaders responseHeaders, ResponseBody responseBody) {
        this.responseLine = responseLine;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public static HttpResponse redirect(final String location) {
        final ResponseHeaders headers = new ResponseHeaders();
        headers.add(LOCATION, location);

        return new HttpResponse(new StatusLine(HTTP_1_1, FOUND), headers, EMPTY_RESPONSE_BODY);
    }

    public static HttpResponse ok(final String location) {
        final String fileExtension = FileIoUtils.getFileExtension(location);
        final String filePath = getFilePath(fileExtension);

        try {
            final byte[] body = FileIoUtils.loadFileFromClasspath(filePath + location);
            return ok(body, ContentType.of(fileExtension));
        } catch (IOException | URISyntaxException | NullPointerException e) {
            return HttpResponse.notFound();
        }
    }

    public static HttpResponse ok(final String location, final Map<String, Object> params) {
        try {
            final String load = HandleBarTemplateLoader.load(location, params);
            final byte[] bytes = load.getBytes(StandardCharsets.UTF_8);
            return ok(bytes, ContentType.HTML);
        } catch (IOException e) {
            return HttpResponse.notFound();
        }
    }

    public static HttpResponse ok(final byte[] body, ContentType contentType) {
        final ResponseHeaders headers = new ResponseHeaders();
        headers.add(CONTENT_TYPE, contentType.getMediaType());
        headers.add(CONTENT_LENGTH, String.valueOf(body.length));

        return new HttpResponse(new StatusLine(HTTP_1_1, OK), headers, new ResponseBody(new String(body)));
    }

    private static HttpResponse notFound() {
        return new HttpResponse(new StatusLine(HTTP_1_1, NOT_FOUND), new ResponseHeaders(), EMPTY_RESPONSE_BODY);
    }

    private static String getFilePath(final String fileExtension) {
        if (fileExtension.endsWith("html") || fileExtension.endsWith("ico")) {
            return TEMPLATES;
        }
        return STATIC;
    }

    public HttpResponse addCookie(final String key, final String value) {
        responseHeaders.add(SET_COOKIE, String.format(COOKIE_FORMAT, key, value));
        return this;
    }

    public void write(final DataOutputStream dos) throws IOException {
        writeStatusLine(dos);
        writeHeaders(dos);
        writeBody(dos);
        dos.flush();
    }

    private void writeStatusLine(final DataOutputStream dos) throws IOException {
        dos.writeBytes(responseLine.value() + CRLF);
    }

    private void writeHeaders(final DataOutputStream dos) throws IOException {
        for (final String header : responseHeaders.getHeaders().keySet()) {
            dos.writeBytes(header + HEADER_DELIMITER + responseHeaders.get(header) + CRLF);
        }
        dos.writeBytes(CRLF);
    }

    private void writeBody(final DataOutputStream dos) throws IOException {
        dos.write(responseBody.getBytes(), 0, responseBody.getBytes().length);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HttpResponse that = (HttpResponse) o;
        return Objects.equals(responseLine, that.responseLine) && Objects.equals(responseHeaders, that.responseHeaders)
            && Objects.equals(responseBody, that.responseBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseLine, responseHeaders, responseBody);
    }
}
