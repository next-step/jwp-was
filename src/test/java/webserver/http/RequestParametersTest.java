package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.request.RequestParameters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


class RequestParametersTest {

    @DisplayName("쿼리 스트링 값을 파싱할수 있다.")
    @ParameterizedTest
    @ValueSource(strings = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net")
    void parse(String value) {
        RequestParameters requestParameters = RequestParameters.of(value);

        assertAll(
                () -> assertThat(requestParameters.get("userId")).isEqualTo("javajigi"),
                () -> assertThat(requestParameters.get("password")).isEqualTo("password")
        );
    }

}
