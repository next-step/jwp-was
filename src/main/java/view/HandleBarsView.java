package view;

import com.github.jknack.handlebars.Template;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class HandleBarsView implements View {

  private Template template;
  private Map params;

  public HandleBarsView(Template template) {
    this(template, Collections.EMPTY_MAP);
  }

  public HandleBarsView(Template template, Map params) {
    this.template = template;
    this.params = params;
  }

  @Override
  public void render(HttpRequest request, HttpResponse response) {
    try {
      byte[] body = template.apply(params).getBytes();
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
