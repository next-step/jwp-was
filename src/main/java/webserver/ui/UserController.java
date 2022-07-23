package webserver.ui;

import org.springframework.http.HttpMethod;
import webserver.domain.HttpRequest;
import webserver.domain.RequestMapping;
import webserver.domain.Response;

public class UserController implements Controller {


    @RequestMapping(value = "/users", method = HttpMethod.GET)
    public Response getUsers(HttpRequest httpRequest) {
        return null;
    }


    @Override
    public Response execute(HttpRequest httpRequest) {
        return null;
    }
}
