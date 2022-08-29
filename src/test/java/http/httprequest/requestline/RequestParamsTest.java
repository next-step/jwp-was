package http.httprequest.requestline;

import http.httprequest.requestline.RequestParams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParamsTest {

    @Test
    @DisplayName("QueryString을 주면 정상적으로 parsing 하고 객체가 생성되는지 확인")
    void parseQueryString() {
        Map<String, String> paramMap = Map.of("userId", "javajigi", "password", "password", "name", "JaeSung");
        RequestParams expected = new RequestParams(paramMap);

        RequestParams result = RequestParams.from("userId=javajigi&password=password&name=JaeSung");

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Empty String을 주면 정상적으로 EmptyMap Params객체가 생성되는지 확인")
    void parsEmptyString() {
        RequestParams expected = new RequestParams(Collections.emptyMap());

        RequestParams result = RequestParams.from("");

        assertThat(result).isEqualTo(expected);
    }
}
