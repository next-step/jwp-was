package servlet;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;

public class StaticResourceServlet extends AbstractHttpServlet {

  private static final String STATIC_RESOURCE_PATH_PREFIX = "./static";

  @Override
  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    byte[] resource = getResource(httpRequest);
    httpResponse.setHttpVersion("HTTP1/1");
    httpResponse.setHttpStatus(resource != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    httpResponse.setContentType(getContentType(httpRequest));
    httpResponse.setContentLength(resource.length);
    httpResponse.setBody(resource);

    httpResponse.render();
  }

  private String getContentType(HttpRequest httpRequest) {
    if (httpRequest.getPath().contains(".css")) {
      return "text/css;charset=utf-8";
    }
    return "text/html;charset=utf-8";
  }

  private byte[] getResource(HttpRequest httpRequest) {
    try {
      return FileIoUtils.loadFileFromClasspath(STATIC_RESOURCE_PATH_PREFIX + httpRequest.getPath());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return null;
  }
}
