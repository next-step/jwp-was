package webserver.response;

import static webserver.request.Protocol.HTTP_1_1;
import static webserver.response.ResponseBody.EMPTY_RESPONSE_BODY;
import static webserver.response.StatusCode.FOUND;
import static webserver.response.StatusCode.NOT_FOUND;
import static webserver.response.StatusCode.OK;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import webserver.request.ContentType;
import webserver.template.HandleBarTemplateLoader;

public class HttpResponse {

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
        headers.add("Location", location);

        return new HttpResponse(new StatusLine(HTTP_1_1, FOUND), headers, EMPTY_RESPONSE_BODY);
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
        headers.add("Content-Type", contentType.getMediaType());
        headers.add("Content-Length", String.valueOf(body.length));

        return new HttpResponse(new StatusLine(HTTP_1_1, OK), headers, new ResponseBody(new String(body)));
    }

    private static HttpResponse notFound() {
        return new HttpResponse(new StatusLine(HTTP_1_1, NOT_FOUND), new ResponseHeaders(), EMPTY_RESPONSE_BODY);
    }

    public HttpResponse addCookie(final String key, final String value) {
        responseHeaders.add("Set-Cookie", String.format("%s=%s; Path=/", key, value));
        return this;
    }

    public void write(final DataOutputStream dos) throws IOException {
        writeStatusLine(dos);
        writeHeaders(dos);
        writeBody(dos);
        dos.flush();
    }

    private void writeStatusLine(final DataOutputStream dos) throws IOException {
        dos.writeBytes(responseLine.value() + "\r\n");
    }

    private void writeHeaders(final DataOutputStream dos) throws IOException {
        for (final String header : responseHeaders.getHeaders().keySet()) {
            dos.writeBytes(header + ": " + responseHeaders.get(header) + "\r\n");
        }
        dos.writeBytes("\r\n");
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
