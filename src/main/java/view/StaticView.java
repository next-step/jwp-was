package view;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;

public class StaticView implements View {

  private static final String STATIC_RESOURCE_PATH_PREFIX = "./static";

  @Override
  public void render(HttpRequest request, HttpResponse response) {
    byte[] resource = getResource(request);

    response.setHttpVersion("HTTP1/1");
    response.setHttpStatus(resource != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    response.setContentType(getContentType(request));
    response.setContentLength(resource.length);
    response.setBody(resource);

    response.render();
  }

  private String getContentType(HttpRequest httpRequest) {
    if (httpRequest.getPath().contains(".css")) {
      return "text/css;charset=utf-8";
    } else if (httpRequest.getPath().contains(".js")) {
      return "text/javascript;charset=utf-8";
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
