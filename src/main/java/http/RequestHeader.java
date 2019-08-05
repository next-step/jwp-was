package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

  private static final int ATTRIBUTE_KEY_INDEX = 0;
  private static final int ATTRIBUTE_VALUE_INDEX = 1;
  private static final String ATTRIBUTE_DELIMITER = ": ";
  private static final String HOST_KEY = "Host";
  private static final String CONNECTION_KEY = "Connection";
  private static final String USER_AGENT_KEY = "User-Agent";
  private static final String ACCEPT_KEY = "Accept";
  private static final String REFERER = "Referer";
  private static final String ACCEPT_ENCODING = "Accept-Encoding";
  private static final String ACCEPT_LANGUAGE = "Accept-Language";


  private String host;
  private String connection;
  private String userAgent;
  private String accept;
  private String referer;
  private String acceptEncoding;
  private String acceptLanguage;

  public RequestHeader(String host, String connection, String userAgent, String accept,
      String referer, String acceptEncoding, String acceptLanguage) {
    this.host = host;
    this.connection = connection;
    this.userAgent = userAgent;
    this.accept = accept;
    this.referer = referer;
    this.acceptEncoding = acceptEncoding;
    this.acceptLanguage = acceptLanguage;
  }

  public static RequestHeader parse(BufferedReader request) throws IOException {
    Map<String, String> attributes = parseAttributes(request);

    return new RequestHeader(attributes.get(HOST_KEY), attributes.get(CONNECTION_KEY),
        attributes.get(USER_AGENT_KEY), attributes.get(ACCEPT_KEY), attributes.get(REFERER),
        attributes.get(ACCEPT_ENCODING), attributes.get(ACCEPT_LANGUAGE));
  }

  private static Map<String, String> parseAttributes(BufferedReader request) throws IOException {
    HashMap<String, String> attributes = new HashMap<>();
    String line = "";
    while ((line = request.readLine()) != null) {
      if ("".equals(line)) {
        break;
      }
      attributes.put(line.split(ATTRIBUTE_DELIMITER)[ATTRIBUTE_KEY_INDEX],
          line.split(ATTRIBUTE_DELIMITER)[ATTRIBUTE_VALUE_INDEX]);
    }
    return attributes;
  }

  public String getHost() {
    return host;
  }

  public String getConnection() {
    return connection;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public String getAccept() {
    return accept;
  }

  public String getReferer() {
    return referer;
  }

  public String getAcceptEncoding() {
    return acceptEncoding;
  }

  public String getAcceptLanguage() {
    return acceptLanguage;
  }
}
