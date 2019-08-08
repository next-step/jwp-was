package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import utils.IOUtils;

public class HttpRequest {

  private RequestLine requestLine;
  private RequestHeader requestHeader;
  private Map<String, String> requestBody;

  public HttpRequest(BufferedReader requestStream) throws IOException {
    requestLine = RequestLine.parse(requestStream.readLine());
    requestHeader = RequestHeader.parse(requestStream);
    requestBody = initRequestBody(requestStream);
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

  public boolean isLogin() {
    return requestHeader.isLogin();
  }

  public Map<String, String> getRequestBody() {
    return requestBody;
  }
}
