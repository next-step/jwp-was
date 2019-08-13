package servlet;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;

public class DefaultServlet extends AbstractHttpServlet {

  private static final String TEMPLATES_PATH_PREFIX = "./templates";

  @Override
  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    byte[] resource = getResource(httpRequest);
    httpResponse.setHttpVersion("HTTP1/1");
    httpResponse.setHttpStatus(resource != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    httpResponse.setContentType("text/html;charset=utf-8");
    httpResponse.setContentLength(resource.length);
    httpResponse.setBody(resource);

    httpResponse.render();
  }

  private byte[] getResource(HttpRequest httpRequest) {
    try {
      return FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH_PREFIX + httpRequest.getPath());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return null;
  }

}
