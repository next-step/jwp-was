package http;

import java.util.Map;
import java.util.Objects;

public class RequestLine {

  private static final String SPACE_DELIMITER = " ";
  private static final int METHOD_INDEX = 0;
  private static final int URL_INDEX = 1;
  private static final int VERSION_INDEX = 2;
  private static final String QUESTION_DELIMITER = "\\?";
  private static final int PATH_INDEX = 0;
  private static final int QUERYSTRING_INDEX = 1;
  private static final int HAS_QUERYSTRING_CONDITION_LENGTH = 1;

  private HttpMethod method;
  private String path;
  private String version;
  private Parameters parameters;


  private RequestLine(String method, String path, String version, Parameters parameters) {
    this.method = HttpMethod.valueOf(method);
    this.path = path;
    this.version = version;
    this.parameters = parameters;
  }

  public static RequestLine parse(String requestLine) {
    String[] requestLineToken = requestLine.split(SPACE_DELIMITER);
    String[] urlToken = requestLineToken[URL_INDEX].split(QUESTION_DELIMITER);

    return new RequestLine(requestLineToken[METHOD_INDEX], urlToken[PATH_INDEX],
        requestLineToken[VERSION_INDEX], makeParameters(urlToken));
  }

  private static Parameters makeParameters(String[] urlToken) {
    return urlToken.length > HAS_QUERYSTRING_CONDITION_LENGTH ? Parameters
        .parse(urlToken[QUERYSTRING_INDEX]) : Parameters.EMPTY;
  }

  public HttpMethod getMethod() {
    return method;
  }

  public String getVersion() {
    return version;
  }

  public String getPath() {
    return path;
  }

  public Map<String, String> getParameters() {
    return parameters.getParameters();
  }

  public boolean isPost() {
    return method.isPost();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestLine that = (RequestLine) o;
    return Objects.equals(method, that.method) &&
        Objects.equals(path, that.path) &&
        Objects.equals(version, that.version) &&
        Objects.equals(parameters, that.parameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(method, path, version, parameters);
  }
}
