package actions;

import db.DataBase;
import webserver.AuthenticationException;
import webserver.HandleBarsViewResolver;
import webserver.RequestHandler;
import webserver.http.Constants;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.response.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserListAction implements Action {

    private HandleBarsViewResolver viewResolver = new HandleBarsViewResolver(Constants.TEMPLATES_PATH, ".html");

    public HttpResponse execute(HttpRequest request) throws IOException {
        if (!request.isLogined()) {
            throw new AuthenticationException();
        }

        Map<String, Object> model = new HashMap<>();
        model.put("users", DataBase.findAll());

        byte[] body = viewResolver.render(request.getPath(), model).getBytes();
        return new HttpResponse(HttpStatus.OK,"text/html", body);
    }
}
