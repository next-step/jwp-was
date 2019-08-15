package view;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.io.IOException;
import utils.HandleBarsRender;

public class HandleBarsView implements View {

  private String path;

  public HandleBarsView(String path) {
    this.path = path;
  }

  @Override
  public void render(HttpRequest request, HttpResponse response) {
    response.setHttpVersion("HTTP1/1");
    
    try {
      String renderView = HandleBarsRender.render(path);
      byte[] view = renderView.getBytes();
      response.setHttpStatus(HttpStatus.OK);
      response.setContentType("text/html;charset=utf-8");
      response.setContentLength(view.length);
      response.setBody(view);
    } catch (IOException e) {
      response.setHttpStatus(HttpStatus.Internal_Server_Error);
    }
    response.render();
  }
}
