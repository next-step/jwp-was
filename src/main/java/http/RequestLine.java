package http;

public class RequestLine {

  private static final String SPACE_DELIMITER = " ";
  private static final int METHOD_INDEX = 0;
  private static final int URL_INDEX = 1;
  private static final int VERSION_INDEX = 2;

  private String method;
  private HttpURL url;
  private String version;

  public RequestLine(String method, String url, String version) {
    this.method = method;
    this.url = HttpURL.parse(url);
    this.version = version;
  }

  public static RequestLine parse(String requestLine) {
    String[] requestLineToken = requestLine.split(SPACE_DELIMITER);
    return new RequestLine(requestLineToken[METHOD_INDEX], requestLineToken[URL_INDEX],
        requestLineToken[VERSION_INDEX]);
  }

  public String getMethod() {
    return method;
  }

  public String getVersion() {
    return version;
  }

  public String getPath() {
    return url.getPath();
  }

  public String getParam(String key) {
    return url.getParam(key);
  }
}
