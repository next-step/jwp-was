package utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestUtilsTest {

    @CsvSource(value = {"userId=hjjang:userId:hjjang", "userId=hjjang&password=password&name=Hyungju:password:password", "userId=hjjang&password=password&name=Hyungju:name:Hyungju", "userId=hjjang&password=:userId:hjjang"}, delimiter = ':')
    @ParameterizedTest
    void 쿼리_스트링_파서_테스트(String queryString, String key, String value) {
        Map<String, String> requestData = HttpRequestUtils.queryStringParser(queryString);

        assertThat(requestData.get(key)).isEqualTo(value);
    }
}