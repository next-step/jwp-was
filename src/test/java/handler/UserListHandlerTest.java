package handler;

import static org.assertj.core.api.Assertions.assertThat;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.view.StaticResourceView;
import http.view.TemplateModel;
import http.view.TemplateView;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import model.User;
import org.junit.jupiter.api.Test;

class UserListHandlerTest {

    private Handler handler = new UserListHandler();

    @Test
    void list_success() throws IOException {
        DataBase.addUser(new User("javajigi", "password", "", ""));
        DataBase.addUser(new User("user2", "password", "", ""));

        HttpRequest httpRequest = createRequest(true);

        HttpResponse httpResponse = handler.handle(httpRequest);

        Collection<User> users = DataBase.findAll();
        TemplateModel templateModel = new TemplateModel();
        templateModel.add("users", users);
        HttpResponse expect = new HttpResponse(new TemplateView("user/list", templateModel));
        assertThat(httpResponse).isEqualTo(expect);
    }

    @Test
    void list_not_login() throws IOException {
        HttpRequest httpRequest = createRequest(false);
        HttpResponse httpResponse = handler.handle(httpRequest);

        HttpResponse expect = new HttpResponse(new TemplateView("login"));
        assertThat(httpResponse).isEqualTo(expect);
    }

    private HttpRequest createRequest(boolean isLogin) throws IOException {
        String line = "GET /user/list HTTP/1.1";
        if (isLogin) {
            line += "\r\nCookie: logined=true";
        }

        return HttpRequest.from(new ByteArrayInputStream(line.getBytes()));
    }
}
