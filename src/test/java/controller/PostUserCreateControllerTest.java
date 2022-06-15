package controller;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.Request;
import webserver.request.RequestBody;
import webserver.request.RequestHeader;
import webserver.request.RequestLine;
import webserver.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

class PostUserCreateControllerTest {

    @DisplayName("POST 회원가입 요청이 성공시, index.html 로 Redirect 된다.")
    @Test
    void doPost() throws IOException {
        // given
        String userInfo = "userId=javajigi"
                + "&password=P@ssw0rD"
                + "&name=JaeSung"
                + "&email=javajigi@slipp.net";
        RequestLine requestLine = RequestLine.from("POST /user/create HTTP/1.1");
        RequestHeader requestHeader = RequestHeader.from(Arrays.asList(
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
                "Content-Length: " + userInfo.getBytes().length
        ));
        RequestBody requestBody = RequestBody.from(userInfo);
        Request request = new Request(requestLine, requestHeader, requestBody);

        // when
        Response response = Container.handle(request);
        String actual = response.toString();

        //then
        String expected = "HTTP/1.1 302 Found \r\n"
                + "Location: /index.html \r\n"
                + "\r\n";
        assertThat(actual).isEqualTo(expected);
    }
}
