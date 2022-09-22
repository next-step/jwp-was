package webserver.http.domain;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUpController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.requestBody();

        String userId = requestBody.body("userId");
        DataBase.addUser(new User(userId, requestBody.body("password"), requestBody.body("name"), requestBody.body("email")));
        httpResponse.sendRedirect("/index.html");
    }
}
