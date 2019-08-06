package webserver.http.request;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import utils.IOUtils;
import webserver.http.EntityHeader;
import webserver.http.EntityHeaderFields;
import webserver.http.GeneralHeader;
import webserver.http.HttpMethod;
import webserver.http.cookie.Cookie;
import webserver.http.cookie.SimpleCookie;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Getter
public class HttpRequest {

    private RequestLine requestLine;
    private GeneralHeader generalHeader;
    private RequestHeader requestHeader;
    private EntityHeader entityHeader;
    private MessageBody messageBody;

    public HttpRequest(final InputStream inputStream) throws IOException {
        final BufferedReader bufferedReader = bufferedReader(inputStream);

        setRequestLine(bufferedReader.readLine());

        final List<String> httpRequestHeaders = readHttpRequestHeaders(bufferedReader);
        setGeneralHeader(httpRequestHeaders);
        setRequestHeader(httpRequestHeaders);
        setEntityHeader(httpRequestHeaders);

        setMessageBody(readMessageBody(bufferedReader));
    }

    public String path() {
        return requestLine.path();
    }

    public String removeExtensionPath() {
        final String DOT = ".";
        if (StringUtils.contains(path(), DOT)) {
            return StringUtils.substring(path(), 0, StringUtils.lastIndexOf(path(), DOT));
        }

        return path();
    }

    public Query query() {
        Query query = messageBody.getQuery();
        if (query.isEmpty()) {
            return requestLine.query();
        }

        return query;
    }

    public HttpMethod httpMethod() {
        return requestLine.getMethod();
    }

    public Cookie getCookie() {
        return new SimpleCookie(entityHeader.get(Cookie.COOKIE));
    }

    private BufferedReader bufferedReader(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return new BufferedReader(inputStreamReader);
    }

    private List<String> readHttpRequestHeaders(BufferedReader bufferedReader) throws IOException {
        String readLine;
        List<String> httpRequestHeaders = new ArrayList<>();

        do {
            readLine = bufferedReader.readLine();
            httpRequestHeaders.add(readLine);
            if (readLine == null || readLine.isEmpty()) {
                break;
            }
        } while (bufferedReader.ready());

        return httpRequestHeaders;
    }

    private String readMessageBody(BufferedReader bufferedReader) throws IOException {
        final String contentLength = entityHeader.get(EntityHeaderFields.CONTENT_LENGTH);

        if (StringUtils.isNumeric(contentLength)) {
            return IOUtils.readData(bufferedReader, Integer.valueOf(contentLength));
        }

        return "";
    }

    private void setRequestLine(final String requestLine) {
        this.requestLine = RequestLine.parse(requestLine);
    }

    private void setGeneralHeader(final List<String> httpRequestHeaders) {
        this.generalHeader = new GeneralHeader(httpRequestHeaders);
    }

    private void setRequestHeader(final List<String> httpRequestHeaders) {
        this.requestHeader = new RequestHeader(httpRequestHeaders);
    }

    private void setEntityHeader(final List<String> httpRequestHeaders) {
        this.entityHeader = new EntityHeader(httpRequestHeaders);
    }

    private void setMessageBody(final String messageBody) throws UnsupportedEncodingException {
        this.messageBody = new MessageBody(messageBody);
    }
}
