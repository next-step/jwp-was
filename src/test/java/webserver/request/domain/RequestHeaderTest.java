package webserver.request.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.exception.StringEmptyException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestHeaderTest {

    @Test
    @DisplayName("header 생성 테스트")
    public void createRequestHeader () {
        String header = "Host: localhost:8080";

        RequestHeader requestHeader = RequestHeader.create(header);

        assertThat(requestHeader).isEqualTo(new RequestHeader(new String[] {"Host", "localhost:8080"}));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "Host:localhost:8080", "Host:"})
    @DisplayName("header 에 빈값이나 속성 값이 없으면 예외를 던진다")
    public void validateRequestHeader(String header) {
        assertThrows(StringEmptyException.class, () -> {
           RequestHeader.create(header);
        });
    }
}
