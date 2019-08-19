package view;

import http.HttpRequest;
import http.HttpResponse;

public class RedirectView implements View {

  String path;

  public RedirectView(String path) {
    this.path = path;
  }

  @Override
  public void render(HttpRequest request, HttpResponse response) {
    response.sendRedirect(path);
  }
}
