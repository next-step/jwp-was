package view;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;

public class StaticView implements View {

  private static final String STATIC_RESOURCE_PATH_PREFIX = "./static";
  private String path;

  public StaticView(String path) {
    this.path = path;
  }

  @Override
  public void render(HttpRequest request, HttpResponse response) {
    response.setHttpVersion("HTTP1/1");

    try {
      byte[] resource = FileIoUtils.loadFileFromClasspath(STATIC_RESOURCE_PATH_PREFIX + path);
      response.setHttpStatus(HttpStatus.OK);
      response.setContentType(getContentType(request));
      response.setContentLength(resource.length);
      response.setBody(resource);
    } catch (IOException e) {
      response.setHttpStatus(HttpStatus.Internal_Server_Error);
    } catch (URISyntaxException e) {
      response.setHttpStatus(HttpStatus.NOT_FOUND);
    }

    response.render();
  }

  private String getContentType(HttpRequest httpRequest) {
    return httpRequest.getAccept();
  }

}
