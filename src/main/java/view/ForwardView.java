package view;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;

public class ForwardView implements View {

  private static final String TEMPLATES_PATH_PREFIX = "./templates";

  private String path;

  public ForwardView(String path) {
    this.path = path;
  }

  @Override
  public void render(HttpRequest request, HttpResponse response) {

    byte[] resource = getResource(path);
    response.setHttpVersion("HTTP1/1");
    response.setHttpStatus(resource != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    response.setContentType("text/html;charset=utf-8");
    response.setContentLength(resource.length);
    response.setBody(resource);

    response.render();
  }

  private byte[] getResource(String path) {
    try {
      return FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH_PREFIX + path);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return null;
  }
}
