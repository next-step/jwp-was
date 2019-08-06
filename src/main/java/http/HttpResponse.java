package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.HandleBarsRender;

public class HttpResponse {

  private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
  private static final String NON_STATIC_RESOURCE_PATH_PREFIX = "./templates";
  private static final String STATIC_RESOURCE_PATH_PREFIX = "./static";
  private DataOutputStream dos;

  private Map<String, String> cookies = new HashMap<>();

  public HttpResponse(DataOutputStream dos) {
    this.dos = dos;
  }

  public void addCookie(String cookieName, String cookieValue) {
    cookies.put(cookieName, cookieValue);
  }

  private void setCookies() {
    if (cookies.isEmpty()) {
      return;
    }
    StringBuffer cookiesString = new StringBuffer();
    cookiesString.append("Set-Cookie: ");
    cookiesString.append(this.cookies.keySet().stream()
        .map(key -> key + "=" + cookies.get(key))
        .collect(Collectors.joining("; ")));
    try {
      dos.writeBytes(cookiesString.toString() + ";\r\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static boolean isStaticResource(String path) {
    if (path == null) {
      return false;
    }
    return path.contains(".css") || path.contains(".js") || path.contains(".eot") || path
        .contains(".svg") || path.contains(".woff") || path.contains(".ttf");
  }

  private void response200Header(int lengthOfBodyContent, boolean isStaticResource) {
    try {
      dos.writeBytes("HTTP/1.1 200 OK \r\n");
      dos.writeBytes(getContentType(isStaticResource));
      dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
      setCookies();
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
      return "Content-Type: text/css;charset=utf-8\r\n";
    }
    return "Content-Type: text/html;charset=utf-8\r\n";
  }

  private String urlConvert(String url) {
    if (isStaticResource(url)) {
      return STATIC_RESOURCE_PATH_PREFIX + url;
    }
    return NON_STATIC_RESOURCE_PATH_PREFIX + url;
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
