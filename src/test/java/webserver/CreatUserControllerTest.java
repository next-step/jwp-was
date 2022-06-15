package webserver;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class CreatUserControllerTest {

    @Test
    void serving() {
        Request request = new Request("GET /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");

        Controller controller = new CreatUserController();

        RequestLine requestLine = request.getRequestLine();
        QueryString queryString = requestLine.toQueryString();

        assertThat(queryString.getPath()).isEqualTo("/create");
        assertThat(queryString.get("userId")).isEqualTo("javajigi");
        assertThat(queryString.get("password")).isEqualTo("password");
        assertThat(queryString.get("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(queryString.get("email")).isEqualTo("javajigi@slipp.net");
    }

}