package model;

import constant.HttpMethod;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpPath;
import request.HttpProtocol;
import request.QueryString;
import request.RequestLine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RequestLineTest {

    @Test
    @DisplayName("Get 메소드 테스트 하기")
    void requestLineByGet() {
        String line = "GET /nextstep?name=김배민&age=3 HTTP/1.1";
        Map<String, Object> param = new HashMap<>();
        param.put("name", "김배민");
        param.put("age", "3");
        QueryString queryString = new QueryString(param);

        RequestLine requestLine = RequestLine.from(line);

        assertAll(() -> {
            assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
            assertThat(requestLine.getHttpPath()).isEqualTo(new HttpPath("/nextstep", queryString));
            assertThat(requestLine.getHttpProtocol()).isEqualTo(new HttpProtocol("HTTP", "1.1"));
        });
    }

    @Test
    @DisplayName("POST 메소드 테스트 하기")
    void requestLineByPost() {
        String line = "POST /nextstep?name=김배민&age=3 HTTP2/1.2";
        Map<String, Object> param = new HashMap<>();
        param.put("name", "김배민");
        param.put("age", "3");
        QueryString queryString = new QueryString(param);

        RequestLine requestLine = RequestLine.from(line);

        assertAll(() -> {
            assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.POST);
            assertThat(requestLine.getHttpPath()).isEqualTo(new HttpPath("/nextstep", queryString));
            assertThat(requestLine.getHttpProtocol()).isEqualTo(new HttpProtocol("HTTP2", "1.2"));
        });

    }

}
