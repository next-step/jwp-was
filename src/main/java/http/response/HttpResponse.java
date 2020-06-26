package http.response;

import http.HttpStatus;
import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.TemplateUtils;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Optional;

public class HttpResponse {
    private static final String SET_COOKIE = "Set-Cookie: ";
    private final static String COOKIE_PATH = "; Path=/";
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final byte[] EMPTY_BODY = {};

    private final HttpRequest request;
    private final DataOutputStream dos;
    private final HttpResponseHeaders responseHeaders;

    private HttpResponse(final HttpRequest request,
                         final DataOutputStream dos,
                         final HttpResponseHeaders responseHeaders) {
        this.request = request;
        this.dos = dos;
        this.responseHeaders = responseHeaders;
    }

    public static HttpResponse of(final HttpRequest request, final OutputStream out) {
        return new HttpResponse(request, new DataOutputStream(out), new HttpResponseHeaders());
    }

    public void addHeader(final String key, final String value) {
        responseHeaders.addHeader(key, value);
    }

    public void addCookie(final String key, final String value) {
        responseHeaders.addCookie(key, value);
    }

    public void templateForward() {
        final byte[] body = TemplateUtils.getTemplateData(request);
        addHeader("Content-Type", "text/html;charset=utf-8");
        addHeader("Content-Length", String.valueOf(body.length));
        response(HttpStatus.OK, body);
    }

    public void staticFileForward() throws Exception {
        final String path = request.getPath();
        final Optional<byte[]> resource = FileIoUtils.loadFileFromClasspath(path);
        if (resource.isPresent()) {
            final byte[] body = resource.get();
            addHeader("Content-Length", String.valueOf(body.length));
            response(HttpStatus.OK, body);
        } else {
            response(HttpStatus.NOT_FOUND, EMPTY_BODY);
        }
    }

    public void sendRedirect(final String redirectUrl) {
        addHeader("Location", redirectUrl);
        response(HttpStatus.MOVED_TEMPORARILY, EMPTY_BODY);
    }

    private void response(final HttpStatus status, final byte[] body) {
        try {
            dos.writeBytes("HTTP/1.1 " + status.getValue() + " " + status.getReasonPhrase() + "\r\n");
            processHeaders();
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processHeaders() {
        try {
            final Collection<String> keys = responseHeaders.getHeaderNames();
            for (String key : keys) {
                dos.writeBytes(key + ": " + responseHeaders.getHeader(key) + " \r\n");
            }
            processSetCookie();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processSetCookie() throws IOException {
        final Collection<String> keys = responseHeaders.getCookieNames();
        for (String key : keys) {
            String str = SET_COOKIE + key + "=" + responseHeaders.getCookie(key) + COOKIE_PATH + "\r\n";
            logger.debug("COOKIE STR ::{}", str);
            dos.writeBytes(str);
        }
    }
}
