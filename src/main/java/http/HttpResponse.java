package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;
    private DataOutputStream dataOutputStream;
    private Set<Cookie> cookies;
    private boolean responseDone = false;

    private HttpResponse(DataOutputStream dataOutputStream) {
        this.httpStatus = HttpStatus.OK;
        this.httpHeaders = HttpHeaders.emptyHeaders();
        this.dataOutputStream = dataOutputStream;
        this.cookies = new LinkedHashSet<>();
    }

    public static HttpResponse from(DataOutputStream dataOutputStream) {
        return new HttpResponse(dataOutputStream);
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setHeader(String name, String value) {
        this.httpHeaders.add(name, value);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setContentType(String contentType) {
        setHeader(HttpHeaders.CONTENT_TYPE, contentType);
    }

    public void setContentLength(long length) {
        setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(length));
    }

    public void addCookie(Cookie cookie) {
        this.cookies.add(cookie);
        this.setHeader(httpHeaders.SET_COOKIE, cookie.getName() + "=" + cookie.getValue() + "; Path=" + cookie.getPath());
    }

    public void sendRedirect(String location) {
        setHeader(HttpHeaders.LOCATION, location);
        setHttpStatus(HttpStatus.FOUND);

        try {
            writeHeader();
        } catch(IOException e) {
            logger.error(e.getMessage());
        }

        responseDone();
    }

    public void writeHeader() throws IOException {
        dataOutputStream.writeBytes("HTTP/1.1 " + httpStatus.getValue() + " " + httpStatus.getMessage() + "\r\n");
        for (String name : httpHeaders.keySet()) {
            List<String> headerValues = httpHeaders.get(name);

            if(headerValues == null) {
                continue;
            }

            for (String headerValue : headerValues) {
                dataOutputStream.writeBytes(name + ": " + headerValue + "\r\n");
            }
        }
        dataOutputStream.writeBytes(System.lineSeparator());
    }

    public boolean isResponseDone() {
        return responseDone;
    }

    public void responseDone() {
        responseDone = true;
        try {
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHeader(String name) {
        return this.httpHeaders.getHeader(name);
    }

    public List<String> getHeaders(String name) {
        return this.httpHeaders.getHeaders(name);
    }

    public MultiValueMap<String, String> getHeaderMap() {
        return this.httpHeaders;
    }

    public Set<Cookie> getCookies() {
        return cookies;
    }
}
