package webserver.domain;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class HttpResponse {
    private static final StringBuilder stringBuilder = new StringBuilder();
    private final HttpStatus httpStatus;
    private final Attributes attributes = new Attributes();
    private final HttpHeaders headers;

    private final View view;

    private final String body;

    public HttpResponse(HttpStatus httpStatus, View view, String body) {
        this.httpStatus = httpStatus;
        this.view = view;
        this.body = body;
        this.headers = HttpHeaders.defaultResponseHeader();
    }

    public static HttpResponse templateResponse(String templateName) {
        ContentType contentType = ContentType.suffixOf(templateName);
        HttpResponse httpResponse = new HttpResponse(HttpStatus.OK, new View(contentType.prefix(), templateName), null);

        httpResponse.addCommonResponseHeader();
        httpResponse.addHeader(HttpHeaders.CONTENT_TYPE, contentType.getContentType());

        return httpResponse;
    }

    public static HttpResponse templateResponse(String templateName, Object param) throws IOException {

        String templateBody = TemplateEngineHelper.applyTemplate( templateName, param);
        HttpResponse httpResponse = new HttpResponse(HttpStatus.OK,
                new View(templateName, templateBody.getBytes()),
                null);

        httpResponse.addCommonResponseHeader();
        httpResponse.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.HTML.getContentType());

        return httpResponse;
    }


    public static HttpResponse of(HttpStatus httpStatus, String viewName) {
        ContentType contentType = ContentType.suffixOf(viewName);
        HttpResponse httpResponse = new HttpResponse(httpStatus, new View(contentType.prefix(), viewName), null);
        httpResponse.addHeader(HttpHeaders.CONTENT_TYPE, contentType.getContentType());

        return httpResponse;
    }

    private void addCommonResponseHeader() {
        headers.setContentLength(getContentLength());
    }

    private int getContentLength() {
        if (view != null) {
            return view.getContent().length;
        }
        if (body != null) {
            return body.length();
        }

        return 0;
    }

    public String getHttpStatusMessage() {
        return "HTTP/1.1 " + httpStatus.value();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getContentType() {
        return headers.getContentType();
    }

    public void addAttribute(String key, String value) {
        attributes.addAttribute(key, value);
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    public void addHeader(String key, String value) {
        headers.add(key, value);
    }

    public String toStringHeader() {
        String lineSeparator = "\r\n";
        stringBuilder.setLength(0);
        stringBuilder.append(getHttpStatusMessage())
                .append(lineSeparator)
                .append(headers.toString())
                .append(lineSeparator);

        return stringBuilder.toString();
    }

    public byte[] getBodyOrView() {
        if (body == null && view == null) {
            return new byte[0];
        }
        if (body != null) {
            return body.getBytes(StandardCharsets.UTF_8);
        }
        return view.getContent();
    }

    public void addCookie(Cookie cookie) {
        addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
