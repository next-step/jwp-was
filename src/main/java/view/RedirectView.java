package view;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;

public class RedirectView implements View {

  private String path;

  public RedirectView(String path) {
    this.path = path;
  }

  @Override
  public void render(HttpRequest request, HttpResponse response) {
    response.setHttpVersion("HTTP1/1");
    response.setHttpStatus(HttpStatus.Found);
    response.setContentType("Content-Type: text/html;charset=utf-8");
    response.setLocation(path);
    response.render();
  }
}
