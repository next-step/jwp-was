package webserver;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParamsTest {
    @CsvSource(value = {"/users?userId=hjjang:userId:hjjang", "/users?userId=hjjang&password=password&name=Hyungju:password:password", "/users?userId=hjjang&password=password&name=Hyungju:name:Hyungju"}, delimiter = ':')
    @ParameterizedTest
    void REQUEST_PARAMS_생성(String requestPath, String key, String value) {
        Map<String, String> requestData = new RequestParams(requestPath).getRequestData();

        assertThat(requestData.get(key)).isEqualTo(value);
    }
}