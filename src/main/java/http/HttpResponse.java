package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.DefaultViewResolver;
import view.View;
import view.ViewResolver;

public class HttpResponse {

  private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

  private static final String NEW_LINE_SYMBOL = "\r\n";
  private static final String COOKIES_JOINING_SYMBOL = ", ";
  private static final String EQUALS_SYMBOL = "=";
  private static final String SET_COOKIE_KEY = "Set-Cookie: ";
  private static final String SPACE = " ";
  private static final String CONTENT_TYPE_WORD = "Content-Type: ";
  private static final String LOCATION_WORD = "Location: ";
  private static final String CONTENT_LENGTH_WORD = "Content-Length: ";
  private static final String NO_VALUE = "";

  private Map<String, String> cookies = new HashMap<>();
  private String httpVersion;
  private HttpStatus httpStatus;
  private String contentType;
  private int contentLength = -1;
  private String location;
  private byte[] body;
  private DataOutputStream dos;

  public HttpResponse(DataOutputStream dos) {
    this.dos = dos;
  }

  private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

  public void addCookie(String cookieName, String cookieValue) {
    cookies.put(cookieName, cookieValue);
  }

  public void sendRedirect(String redirectUrl) {
    setHttpVersion("HTTP1/1");
    setHttpStatus(HttpStatus.Found);
    setContentType("Content-Type: text/html;charset=utf-8");
    setLocation(redirectUrl);
    render();
  }

  public void error(HttpStatus status) {
    setHttpVersion("HTTP1/1");
    setHttpStatus(status);
    render();
  }

  public void render() {
    String httpHeaders = getHttpHeaders();
    byte[] body = getBody();
    try {
      dos.writeBytes(httpHeaders);
      dos.writeBytes(NEW_LINE_SYMBOL);
      if (body != null) {
        dos.write(body, 0, body.length);
      }
      dos.flush();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public String getHttpHeaders() {
    StringBuffer result = new StringBuffer();
    result.append(getStatusLineInfo()).append(NEW_LINE_SYMBOL);
    result.append(getCookiesInfo());
    result.append(getContentTypeInfo());
    result.append(getLocationInfo());
    result.append(getContentLengthWord());

    return result.toString();
  }


  private String getContentTypeInfo() {
    if (contentType == null) {
      return NO_VALUE;
    }
    return CONTENT_TYPE_WORD + contentType + NEW_LINE_SYMBOL;
  }

  private String getLocationInfo() {
    if (location == null) {
      return NO_VALUE;
    }
    return LOCATION_WORD + location + NEW_LINE_SYMBOL;
  }

  private String getContentLengthWord() {
    if (getContentLength() == -1) {
      return NO_VALUE;
    }
    return CONTENT_LENGTH_WORD + contentLength + NEW_LINE_SYMBOL;
  }

  private String getCookiesInfo() {
    if (cookies.isEmpty()) {
      return NO_VALUE;
    }
    StringBuffer cookiesInfo = new StringBuffer();
    cookiesInfo.append(SET_COOKIE_KEY);
    cookiesInfo.append(this.cookies.keySet().stream()
        .map(key -> key + EQUALS_SYMBOL + cookies.get(key))
        .collect(Collectors.joining(COOKIES_JOINING_SYMBOL)));
    cookiesInfo.append(NEW_LINE_SYMBOL);
    return cookiesInfo.toString();
  }

  private String getStatusLineInfo() {
    StringBuffer statusInfo = new StringBuffer();
    statusInfo.append(httpVersion).append(SPACE)
        .append(httpStatus.getStatusCode()).append(SPACE)
        .append(httpStatus.name());
    return statusInfo.toString();
  }

  public Map<String, String> getCookies() {
    return cookies;
  }

  public void setCookies(Map<String, String> cookies) {
    this.cookies = cookies;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public int getContentLength() {
    return contentLength;
  }

  public void setContentLength(int contentLength) {
    this.contentLength = contentLength;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public byte[] getBody() {
    return body;
  }

  public void setBody(byte[] body) {
    this.body = body;
  }

  public String getHttpVersion() {
    return httpVersion;
  }

  public void setHttpVersion(String httpVersion) {
    this.httpVersion = httpVersion;
  }

}

