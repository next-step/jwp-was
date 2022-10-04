package webserver.servlet;

import com.google.common.base.Charsets;
import db.DataBase;
import model.User;
import webserver.http.HttpHeaders;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;

import java.net.URLDecoder;

public class SignUpServlet extends HttpServlet {

    private static final String API_PATH = "/user/create";
    private static final String REDIRECT_PATH = "/index.html";

    @Override
    public String getRequestPath() {
        return API_PATH;
    }

    @Override
    protected HttpResponse doPost(HttpRequest request) {
        User user = convertRequestBodyToUser(request.getBody());

        DataBase.addUser(user);

        HttpHeaders httpHeaders = HttpHeaders.redirect(REDIRECT_PATH);
        return HttpResponse.redirect(httpHeaders);
    }

    private User convertRequestBodyToUser(RequestBody requestBody) {
        String userId = decode(requestBody.getContent("userId"));
        String password = decode(requestBody.getContent("password"));
        String name = decode(requestBody.getContent("name"));
        String email = decode(requestBody.getContent("email"));

        return new User(userId, password, name, email);
    }

    private String decode(String input) {
        return URLDecoder.decode(input, Charsets.UTF_8);
    }
}
