package http;

public class HttpRequest {

  private RequestLine requestLine;
  Parameters parameters;
  RequestHeader requestHeader;

  public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, String requestBody) {
    this.requestLine = requestLine;
    this.requestHeader = requestHeader;
    this.parameters =
        requestBody == null ? requestLine.getParameters() : Parameters.parse(requestBody);
  }

  public Parameters getParameters() {
    return parameters;
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
}
