package http;

public class HttpURL {

  private static final int PATH_INDEX = 0;
  private static final int QUERYSTRING_INDEX = 1;
  private static final int HAS_QUERYSTRING_CONDITION_LENGTH = 1;
  private static final String QUESTION_DELIMITER = "\\?";
  private static final String DEFAULTS_QUERYSTRING = "";

  private String path;
  private Parameters queryString;

  public HttpURL(String[] urlToken) {
    this.path = urlToken[PATH_INDEX];
    this.queryString = Parameters.parse(
        urlToken.length > HAS_QUERYSTRING_CONDITION_LENGTH ? urlToken[QUERYSTRING_INDEX] : DEFAULTS_QUERYSTRING);
  }

  public static HttpURL parse(String url) {
    return new HttpURL(url.split(QUESTION_DELIMITER));
  }

  public String getPath() {
    return path;
  }

  public String getParameter(String key) {
    return queryString.getParameter(key);
  }
}
