package servlet;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;
import view.ForwardView;
import view.View;

public class DefaultServlet extends AbstractHttpServlet {

  @Override
  public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    return new ForwardView(httpRequest.getPath());
  }
}
