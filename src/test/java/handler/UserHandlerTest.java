package handler;


import static org.assertj.core.api.Assertions.assertThat;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.RedirectHttpResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import model.User;
import org.junit.jupiter.api.Test;

class UserHandlerTest {

    @Test
    void create() throws IOException {
        String line = "POST /user/create HTTP/1.1";
        String header = "Content-Length: 91\nContent-Type: application/x-www-form-urlencoded";
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        String raw = line+"\n" +header +"\n\n"+ body;
        InputStream in = new ByteArrayInputStream(raw.getBytes());

        HttpRequest httpRequest = HttpRequest.from(in);

        UserHandler handler = new UserHandler();
        handler.create(httpRequest);

        User user = DataBase.findUserById("javajigi");
        assertThat(user.getUserId()).isEqualTo("javajigi");
    }


    @Test
    void create_redirect() throws IOException {
        String line = "POST /user/create HTTP/1.1";
        String header = "Content-Length: 91\nContent-Type: application/x-www-form-urlencoded";
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        String raw = line+"\n" +header +"\n\n"+ body;
        InputStream in = new ByteArrayInputStream(raw.getBytes());

        HttpRequest httpRequest = HttpRequest.from(in);

        UserHandler handler = new UserHandler();
        HttpResponse httpResponse = handler.create(httpRequest);

        assertThat(httpResponse).isEqualTo(new RedirectHttpResponse("/index.html"));
    }

}
