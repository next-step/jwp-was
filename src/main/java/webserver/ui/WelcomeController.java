package webserver.ui;

import org.springframework.http.HttpMethod;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.RequestMapping;

public class WelcomeController implements Controller {

    @RequestMapping(value = "/index.html", method = HttpMethod.GET)
    public HttpResponse index(HttpRequest httpRequest) {

        return HttpResponse.templateResponse("/index.html");
    }
}
