package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.HandleBarsRender;

public class HttpResponse {

  private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
  private static final String TEMPLATES_PATH_PREFIX = "./templates";
  private static final String STATIC_RESOURCE_PATH_PREFIX = "./static";
  private static final String CSS_CONTENT_TYPE = "Content-Type: text/css;charset=utf-8";
  private static final String TEXT_CONTENT_TYPE = "Content-Type: text/html;charset=utf-8";
  private static final String NEW_LINE_SYMBOL = "\r\n";
  private static final String COOKIES_JOINING_SYMBOL = "; ";
  private static final String EQUALS_SYMBOL = "=";
  private static final String SET_COOKIE_KEY = "Set-Cookie: ";

  private static final List<String> STATIC_RESOURCE_EXTENSION = Arrays
      .asList(".css", ".js", ".eot", ".svg", ".woff", ".ttf");
  private Map<String, String> cookies = new HashMap<>();
  private DataOutputStream dos;

  public HttpResponse(DataOutputStream dos) {
    this.dos = dos;
  }

  public void addCookie(String cookieName, String cookieValue) {
    cookies.put(cookieName, cookieValue);
  }

  private static boolean isStaticResource(String path) {
    if (path == null) {
      return false;
    }
    return STATIC_RESOURCE_EXTENSION.stream()
        .anyMatch(extension -> path.contains(extension));
  }

  private void writeCookies() {
    if (cookies.isEmpty()) {
      return;
    }
    StringBuffer cookiesString = new StringBuffer();
    cookiesString.append(SET_COOKIE_KEY);
    cookiesString.append(this.cookies.keySet().stream()
        .map(key -> key + EQUALS_SYMBOL + cookies.get(key))
        .collect(Collectors.joining(COOKIES_JOINING_SYMBOL)));

    try {
      dos.writeBytes(cookiesString.toString() + ";\r\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void response200Header(int lengthOfBodyContent, boolean isStaticResource) {
    try {
      dos.writeBytes("HTTP/1.1 200 OK \r\n");
      dos.writeBytes(getContentType(isStaticResource));
      dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
      writeCookies();
      dos.writeBytes("\r\n");
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public void sendRedirect(String redirectUrl) {
    try {
      dos.writeBytes("HTTP/1.1 302 Found \r\n");
      dos.writeBytes("Location: " + redirectUrl);
      dos.writeBytes("\r\n");
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }


  private void responseBody(byte[] body) {
    try {
      dos.write(body, 0, body.length);
      dos.flush();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  private String getContentType(boolean isStaticResource) {
    if (isStaticResource) {
      return CSS_CONTENT_TYPE + NEW_LINE_SYMBOL;
    }
    return TEXT_CONTENT_TYPE + NEW_LINE_SYMBOL;
  }

  private String urlConvert(String url) {
    if (isStaticResource(url)) {
      return STATIC_RESOURCE_PATH_PREFIX + url;
    }
    return TEMPLATES_PATH_PREFIX + url;
  }

  public void forward(String url) {
    try {
      byte[] body = FileIoUtils.loadFileFromClasspath(urlConvert(url));
      response200Header(body.length, isStaticResource(url));
      responseBody(body);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  public void handleBarView(String url) {
    try {
      String renderView = HandleBarsRender.render(url);
      byte[] view = renderView.getBytes();
      response200Header(view.length, isStaticResource(url));
      responseBody(view);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
