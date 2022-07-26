package utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestUtilsTest {

    @Test
    void 쿼리_스트링_파서_테스트() {
        Map<String, String> requestData = HttpRequestUtils.queryStringParser("userId=hjjang&password=password&name=Hyungju");

        assertThat(requestData.get("userId")).isEqualTo("hjjang");
        assertThat(requestData.get("password")).isEqualTo("password");
        assertThat(requestData.get("name")).isEqualTo("Hyungju");
    }
}