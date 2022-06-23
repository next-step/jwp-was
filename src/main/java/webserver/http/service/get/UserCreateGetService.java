package webserver.http.service.get;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestParameters;
import webserver.http.response.HttpResponse;

public class UserCreateGetService extends GetService {
    @Override
    protected boolean pathMatch(HttpRequest httpRequest) {
        return httpRequest.getPath().equals("/user/create");
    }

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestParameters requestParameters = httpRequest.getRequestParameters();
        User user = new User(requestParameters.get("userId"),
                requestParameters.get("password"),
                requestParameters.get("name"),
                requestParameters.get("email"));

        DataBase.addUser(user);

        httpResponse.ok(null);
    }
}
