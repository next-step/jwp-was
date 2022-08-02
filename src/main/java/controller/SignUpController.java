package controller;

import db.DataBase;
import model.User;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.QueryParameter;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;

import java.util.Map;

public class SignUpController {

    public static final String url = "/user/create";
    public static final String viewPath = "/user/form.html";
    private static final String redirectPath = "/index.html";

    public HttpResponse run(HttpRequest httpRequest) {
        final HttpMethod httpMethod = httpRequest.getRequestLine().getMethod();

        if (httpMethod.equals(HttpMethod.GET)) {
            return doGet(httpRequest.getRequestLine().getUrl().getQueryParameter());
        } else if (httpMethod.equals(httpMethod.POST)) {
            return doPost(httpRequest.getRequestBody());
        } else {
            throw new IllegalArgumentException();
        }
    }

    private HttpResponse doGet(QueryParameter queryParameter) {
        Map<String, String> parameters = queryParameter.getParameters();
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        String name = parameters.get("name");
        String email = parameters.get("email");

        User user = new User.Builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .build();

        return HttpResponse.getView(viewPath);
    }

    private HttpResponse doPost(RequestBody requestBody) {
        Map<String, String> body = requestBody.getContents();
        String userId = body.get("userId");
        String password = body.get("password");
        String name = body.get("name");
        String email = body.get("email");

        User user = new User.Builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .build();

        DataBase.addUser(user);

        return HttpResponse.redirect(redirectPath);
    }
}
