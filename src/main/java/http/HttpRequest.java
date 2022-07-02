package http;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {

  private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

  private final RequestLine requestLine;

  private final Map<String, String> headers;

  private final String requestBody;

  public HttpRequest(final RequestLine requestLine, final Map<String, String> requestHeaders, final String requestBody) {
    this.requestLine = requestLine;
    this.headers = requestHeaders;
    this.requestBody = requestBody;
  }

  public RequestLine getRequestLine() {
    return requestLine;
  }

  public String getPath() {
    return requestLine.getPath();
  }

  public MediaType getContentType() {
    if (headers == null) {
      return MediaType.TEXT_HTML_UTF8;
    }

    return new MediaType(getAccept());
  }

  private String getAccept() {
    return headers.getOrDefault("Accept", "")
        .split(",")[0];
  }

  public String getCookie() {
    if (headers == null) {
      return "";
    }
    return headers.getOrDefault("Cookie", "");
  }

  public String getRequestBody() {
    if (headers == null) {
      return "";
    }
    return requestBody;
  }
}
