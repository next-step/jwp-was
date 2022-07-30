package controller;

import model.User;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.QueryParameter;
import webserver.http.request.RequestBody;

import java.util.Map;

public class SignUpController {

    public static final String path = "/user/create";

    public void run(HttpRequest httpRequest) {
        final HttpMethod httpMethod = httpRequest.getRequestLine().getMethod();

        if (httpMethod.equals(HttpMethod.GET)) {
            doGet(httpRequest.getRequestLine().getUrl().getQueryParameter());
            return;
        } else if (httpMethod.equals(httpMethod.POST)) {
            doPost(httpRequest.getRequestBody());
            return;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void doGet(QueryParameter queryParameter) {
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

    }

    public void doPost(RequestBody requestBody) {
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
    }
}
