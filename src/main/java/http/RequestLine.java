package http;

public class RequestLine {

  private static final String DELIMITER = " ";

  private String method;
  private String uri;
  private String version;

  public RequestLine(String method, String uri, String version) {
    this.method = method;
    this.uri = uri;
    this.version = version;
  }

  public static RequestLine parse(String requestLine) {
    String[] requestLineToken = requestLine.split(DELIMITER);
    return new RequestLine(requestLineToken[0], requestLineToken[1], requestLineToken[2]);
  }

  public String getMethod() {
    return this.method;
  }

  public String getUri() {
    return this.uri;
  }

  public String getVersion() {
    return this.version;
  }
}
