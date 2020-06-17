package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class StringParseUtilsTest {
    @Test
    @DisplayName("Reqeust Parameter 값 파싱")
    void parseRequestParam() {
        final Map<String, String> requestParams = StringParseUtils.parseRequestParam("userId=jinwoo&pass=1q2w3e4r");
        assertThat(requestParams.get("userId")).isEqualTo("jinwoo");
        assertThat(requestParams.get("pass")).isEqualTo("1q2w3e4r");
    }

    @Test
    @DisplayName("Cookie 값 파싱")
    void parseCookieParam() {
        final Map<String, String> requestParams = StringParseUtils.parseCookies("userId=jinwoo;pass=1q2w3e4r");
        assertThat(requestParams.get("userId")).isEqualTo("jinwoo");
        assertThat(requestParams.get("pass")).isEqualTo("1q2w3e4r");
    }

    @Test
    @DisplayName("이상한 값으로 파싱하면 파싱이 되지 않음")
    void parseFailWhenValueIsNotValid() {
        final Map<String, String> requestParams = StringParseUtils.parseRequestParam("userId/jinwoo");
        assertThat(requestParams.isEmpty()).isTrue();
    }
}
