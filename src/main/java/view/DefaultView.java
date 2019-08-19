package view;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;

public class DefaultView implements View {

  byte[] body;

  public DefaultView(byte[] body) {
    this.body = body;
  }

  @Override
  public void render(HttpRequest request, HttpResponse response) {
    if (body == null) {
      response.error(HttpStatus.NOT_FOUND);
      return;
    }
    response.setHttpVersion("HTTP1/1");
    response.setHttpStatus(HttpStatus.OK);
    response.setContentType("text/html;charset=utf-8");
    response.setContentLength(body.length);
    response.setBody(body);
    response.render();
  }

}
