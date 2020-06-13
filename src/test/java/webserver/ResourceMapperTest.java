package webserver;

import http.RequestLine;
import http.RequestMessage;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceMapperTest {

    @DisplayName("요청 uri가 정적자원을 의미하는 경우 해당되는 정적자원을 byte 형태로 반환")
    @Test
    void test_get_resource_service() throws IOException, URISyntaxException {
        // given
        RequestMessage requestMessage = RequestMessage.of(RequestLine.from("GET /index.html HTTP/1.1"), null);

        byte[] bytes = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        // when
        byte[] resource = ResourceMapper.getResource(requestMessage);
        // then
        assertThat(resource).isEqualTo(bytes);
    }

    @DisplayName("요청 uri에 매핑되는 정적자원이 없는 경우 Not Found를 byte 형태로 반환")
    @Test
    void test_get_nonexistent_resource() {
        // given
        RequestMessage requestMessage = RequestMessage.of(RequestLine.from("GET /ho.html HTTP/1.1"), null);

        byte[] bytes = "Not Found".getBytes();
        // when
        byte[] resource = ResourceMapper.getResource(requestMessage);
        // then
        assertThat(resource).isEqualTo(bytes);
    }

    @DisplayName("GET 방식으로 회원가입 요청 처리")
    @Test
    void test_userCreate_by_get_should_pass() {
        // given
        RequestMessage requestMessage = RequestMessage.of(RequestLine.from("GET /user/create?userId=crystal&password=password&name=%EC%9E%84%EC%88%98%EC%A0%95&email=crystal%40naver.com HTTP/1.1"), null);

        User user = new User("crystal", "password", "임수정", "crystal@naver.com");
        // when
        byte[] body = ResourceMapper.getResource(requestMessage);
        // then
        assertThat(body).isEqualTo(user.toString().getBytes());
    }
}