package webserver.ui;

import org.springframework.http.HttpMethod;
import webserver.domain.HttpRequest;
import webserver.domain.RequestMapping;
import webserver.domain.Response;

public class WelcomeController implements Controller {

    @RequestMapping(value = "/index.html", method = HttpMethod.GET)
    public Response index(HttpRequest httpRequest) {
        return new Response("index.html");
    }
}
