package view;

import com.github.jknack.handlebars.Template;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.io.IOException;

public class HandleBarsView implements View {

  private Template template;

  public HandleBarsView(Template template) {
    this.template = template;
  }

  @Override
  public void render(HttpRequest request, HttpResponse response) {
    try {
      byte[] body = template.apply(request.getAttributes()).getBytes();
      response.setHttpStatus(HttpStatus.OK);
      response.setHttpVersion("HTTP1/1");
      response.setContentType("text/html;charset=utf-8");
      response.setContentLength(body.length);
      response.setBody(body);
      response.render();
    } catch (IOException e) {
      response.error(HttpStatus.Internal_Server_Error);
    }
  }
}
