package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.RequestHeaderParser;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeaderParserTest {

    @Test
    @DisplayName("getContentLegth가 포함된 헤더를 파싱한다.")
    public void getContentLength_테스트() {
        String line = "Content-Length: 59";
        assertThat(HttpHeaderParser.getContentLength(line)).isEqualTo(59);
    }

    @Test
    @DisplayName("cookie에 logined에 대한 정보를 파싱한다.")
    public void isLogin_테스트() {
        String line = "Cookie: logined=false; " +
                "Webstorm-9b394537=6ae36361-f1fc-4841-a5a1-bbac95de2717; " +
                "Idea-d8046071=1686c5bf-7d24-4276-beb0-70273a0fd35d";
        assertThat(HttpHeaderParser.isLogin(line)).isFalse();
    }
}
