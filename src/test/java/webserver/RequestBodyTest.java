package webserver;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.RequestBody;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {

    @CsvSource(value = {"userId=hjjang:userId:hjjang", "userId=hjjang&password=password&name=Hyungju:password:password", "userId=hjjang&password=password&name=Hyungju:name:Hyungju"}, delimiter = ':')
    @ParameterizedTest
    void REQUEST_BODY_생성및조회(String requestBodyString, String key, String value) {
        RequestBody requestBody = new RequestBody(requestBodyString);

        assertThat(requestBody.getParameter(key)).isEqualTo(value);
    }
}