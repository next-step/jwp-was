package webserver.http.response;

import org.apache.commons.lang3.StringUtils;
import webserver.http.*;
import webserver.http.cookie.Cookie;
import webserver.http.cookie.SimpleCookie;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpResponse {

    private static final String CRLF = "\r\n";

    private final StatusLine statusLine;
    private final GeneralHeader generalHeader;
    private final ResponseHeader responseHeader;
    private final EntityHeader entityHeader;

    public HttpResponse(final HttpVersion httpVersion) {
        this.statusLine = new StatusLine(httpVersion);
        this.generalHeader = new GeneralHeader();
        this.responseHeader = new ResponseHeader();
        this.entityHeader = new EntityHeader();
    }

    public void selectHttpStatus(final HttpStatus httpStatus) {
        statusLine.selectHttpStatus(httpStatus);
    }

    public void put(final GeneralHeaderFields field, final CacheResponseDirective value) {
        generalHeader.put(field, value);
    }

    public void put(final ResponseHeaderFields field, final String value) {
        responseHeader.put(field, value);
    }

    public void put(final EntityHeaderFields field, final String value) {
        entityHeader.put(field, value);
    }

    public void put(final String key, final String value) {
        entityHeader.put(key, value);
    }

    public void flush(final OutputStream outputStream, final byte[] responseBody) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        final String rowHeader = rowHeader();
        dataOutputStream.writeBytes(rowHeader);
        dataOutputStream.write(responseBody, 0, responseBody.length);
        dataOutputStream.flush();
    }

    public void setCookie(final String value) {
        entityHeader.put(Cookie.SET_COOKIE, value);
    }

    public Cookie getCookie() {
        return new SimpleCookie(entityHeader.get(Cookie.COOKIE));
    }

    private String rowHeader() {
        final List<String> headers = Arrays.asList(
                statusLine.toString(),
                generalHeader.toString(),
                responseHeader.toString(),
                entityHeader.toString());

        final String rowHeader = headers.stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(CRLF));

        return rowHeader + CRLF;
    }
}
