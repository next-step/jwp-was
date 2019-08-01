package http;

public class RequestLine {

  private String requestLine;

  public RequestLine(String requestLine) {
    this.requestLine = requestLine;

  }

  public static RequestLine parse(String requset) {
    return new RequestLine(requset);
  }

  public String getMethod() {
    return requestLine.split(" ")[0];
  }

  public String getPath() {
    return requestLine.split(" ")[1];
  }
}
