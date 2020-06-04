package model;

import http.parsers.RequestParser;
import http.requests.RequestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("사용자 파싱하는 클래스 테스트")
class UserParserTest {

    @DisplayName("요청을 통해 생성된 user 모델과 같은 조건으로 생성한 user 모델이 동일한지 테스트")
    @Test
    void test_that_model_is_created() {
        // given
        final String rawRequestLine = "GET /user/create?userId=hyeyoom&password=1234abcd&name=ChihoWOn&email=neoul_chw%40icloud.com HTTP/1.1";
        final List<String> rawRequestHeaders = new ArrayList<>();
        rawRequestHeaders.add("Host: some_where_i_belong:8080");
        rawRequestHeaders.add("Connection: keep-alive");
        rawRequestHeaders.add("Cache-Control: no-cache");
        rawRequestHeaders.add("x-my-message: 밥먹고-싶은데-밥통에-밥이-없다");
        final RequestContext requestContext = RequestParser.parse(rawRequestLine, rawRequestHeaders);

        final User userFromRequest = UserParser.parse(requestContext);
        final User userFromManual = new User("hyeyoom", "1234abcd", "ChihoWOn", "neoul_chw@icloud.com");

        // 공장 생산 사용자와 수제 생산 사용자가 같으면 성공!
        assertThat(userFromRequest).isEqualTo(userFromManual);
    }
}