package webserver;

import java.io.*;
import java.util.Map;

public class HttpRequest {

    private HttpHeader header;
    private RequestLine requestLine;
    private RequestBody body;

    public HttpRequest(HttpHeader header, RequestLine requestLine) {
        this.header = header;
        this.requestLine = requestLine;
    }

    public HttpRequest(HttpHeader header, RequestLine requestLine, RequestBody body, Cookie cookie) {
        this.header = header;
        this.requestLine = requestLine;
        this.body = body;
    }

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = br.readLine();

        RequestLine requestLine = RequestLine.parse(line);

        HttpHeader httpHeader = new HttpHeader();
        if (line == null) {
            throw new IOException();
        }
        while (!"".equals(line)) {
            line = br.readLine();
            httpHeader.addHeader(line);
        }


        final RequestBody requestBody = new RequestBody();
        if (requestLine.getMethod().isPost()) {
            requestBody.toBody(br, httpHeader);
            requestBody.createParameter(requestBody.getBody());
        }

        if (httpHeader.getValue(Cookie.COOKIE).length() > 1) {
            Cookie cookie = Cookie.parse(httpHeader.getValue(Cookie.COOKIE));
            httpHeader.setCookie(cookie);
        }

        this.header = httpHeader;
        this.requestLine = requestLine;
        this.body = requestBody;

    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath().getPath();
    }

    public String getHeader(String name) {
        return header.getValue(name);
    }

    public String getBodyParameter(String name) {
        return body.getParameterValue(name);
    }

    public String getQueryParameter(String name) {
        Map<String, String> queryData = requestLine.getPath().getQueryString().getQueryData();
        return queryData.get(name);
    }

    public RequestBody getBody() {
        return body;
    }

    public Cookie getCookie() {
        return header.getCookie();
    }

    public HttpSessionStorage getSessionStorage() {
        return new HttpSessionStorage();
    }

    public HttpSession getSession() {
        String jssessionid = header.getCookie().getValue(HttpSession.JSESSION_ID);
        HttpSession session = getSessionStorage().getSession(jssessionid);
        return session;
    }

    public Boolean isLogined(HttpSession session) {
        Object attribute = session.getAttribute(HttpSession.ATTRIBUTE_LOGINED);
        return Boolean.parseBoolean((String) attribute);
    }

    public HttpSession createHttpSession() {
        return new HttpSession();
    }
}
