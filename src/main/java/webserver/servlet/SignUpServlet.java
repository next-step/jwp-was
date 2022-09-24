package webserver.servlet;

import com.google.common.base.Charsets;
import db.DataBase;
import model.User;
import webserver.http.HttpHeaders;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseLine;

import java.net.URLDecoder;
import java.util.Map;

public class SignUpServlet extends HttpServlet {

    private static final String API_PATH = "/user/create";
    private static final String REDIRECT_PATH = "/index.html";

    @Override
    public String getRequestPath() {
        return API_PATH;
    }

    @Override
    protected HttpResponse doPost(HttpRequest request) {
        Map<String, String> requestBody = request.getBody().getContents();

        User user = convertRequestBodyToUser(requestBody);

        DataBase.addUser(user);

        return new HttpResponse(ResponseLine.redirect(), HttpHeaders.redirect(REDIRECT_PATH));
    }

    private User convertRequestBodyToUser(Map<String, String> requestBody) {
        String userId = decode(requestBody.get("userId"));
        String password = decode(requestBody.get("password"));
        String name = decode(requestBody.get("name"));
        String email = decode(requestBody.get("email"));

        return new User(userId, password, name, email);
    }

    private String decode(String input) {
        return URLDecoder.decode(input, Charsets.UTF_8);
    }
}
