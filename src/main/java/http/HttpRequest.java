package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import utils.IOUtils;

public class HttpRequest {

  public static final String SESSION_COOKIE_ID = "JSESSIONID";

  private RequestLine requestLine;
  private RequestHeader requestHeader;
  private Map<String, String> requestBody;
  private HttpSession httpSession;

  public HttpRequest(BufferedReader requestStream, HttpSessions httpSessions) throws IOException {
    requestLine = RequestLine.parse(requestStream.readLine());
    requestHeader = RequestHeader.parse(requestStream);
    requestBody = initRequestBody(requestStream);
    httpSession = httpSessions.get(getSessionKey());
  }

  private Map<String, String> initRequestBody(BufferedReader requestStream) throws IOException {
    if (!requestLine.isPost()) {
      return Collections.EMPTY_MAP;
    }
    String requestBody = IOUtils.readData(requestStream, requestHeader.getContentLength());
    return Parameters.parse(requestBody).getParameters();
  }

  public Map<String, String> getParameters() {
    if (requestLine.isPost()) {
      return getRequestBody();
    }
    return requestLine.getParameters();
  }

  public RequestLine getRequestLine() {
    return requestLine;
  }

  public RequestHeader getRequestHeader() {
    return requestHeader;
  }

  public String getPath() {
    return requestLine.getPath();
  }

  public boolean isPost() {
    return requestLine.isPost();
  }

  public Map<String, String> getRequestBody() {
    return requestBody;
  }

  public HttpMethod getMethod() {
    return requestLine.getMethod();
  }

  public boolean isGet() {
    return requestLine.isGet();
  }

  public Map<String, String> getCookies() {
    return requestHeader.getCookies();
  }

  public String getAccept() {
    return requestHeader.getAccept();
  }

  public String getSessionKey() {
    return requestHeader.getSessionKey(SESSION_COOKIE_ID);
  }

  public boolean hasSession() {
    return httpSession != null;
  }

  public HttpSession getHttpSession() {
    return httpSession;
  }

}
