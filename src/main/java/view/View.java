package view;

import http.HttpRequest;
import http.HttpResponse;

public interface View {

  void render(HttpRequest request, HttpResponse response);

}
