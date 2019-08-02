package http;

public class HttpURL {

  private static final int PATH_INDEX = 0;
  private static final int QUERYSTRING_INDEX = 1;
  private static final int HAS_QUERYSTRING_CONDITION_LENGTH = 1;
  private static final String QUESTION_DELIMITER = "\\?";
  private static final String DEFAULTS_QUERYSTRING = "";

  private String path;
  private Parameters parameters;

  private HttpURL(String path, Parameters parameters) {
    this.path = path;
    this.parameters = parameters;
  }

  public static HttpURL parse(String url) {
    String[] urlToken = url.split(QUESTION_DELIMITER);
    return new HttpURL(urlToken[PATH_INDEX], Parameters.parse(
        urlToken.length > HAS_QUERYSTRING_CONDITION_LENGTH ? urlToken[QUERYSTRING_INDEX]
            : DEFAULTS_QUERYSTRING));
  }

  public String getPath() {
    return path;
  }

  public String getParameter(String key) {
    return parameters.getParameter(key);
  }
}
