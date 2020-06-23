package http.response;

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

    public void templateForward() {
        final byte[] body = TemplateUtils.getTemplateData(request);
        addHeader("Content-Type", "text/html;charset=utf-8");
        response200Header(body.length);
        responseBody(body);
    }

    public void staticFileForward() throws Exception {
        final String path = request.getPath();
        final Optional<byte[]> resource = FileIoUtils.loadFileFromClasspath(path);
        if (resource.isPresent()) {
            final byte[] body = resource.get();
            response200Header(body.length);
            responseBody(body);
        } else {
            response404Header();
        }
    }

    private void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            processHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void addCookie(final String key, final String value) {
        responseHeaders.addCookie(key, value);
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

    public void sendRedirect(final String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            processHeaders();
            dos.writeBytes("Location: " + redirectUrl + " \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            processHeaders();
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response404Header() {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found \r\n");
            processHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}
