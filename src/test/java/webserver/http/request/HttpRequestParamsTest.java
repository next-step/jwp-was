package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestParamsTest {

    @DisplayName("key, value 확인")
    @Test
    void of() {
        String paramString = "userId=1234&password=1234&email=1234";
        HttpRequestParams requestParams = HttpRequestParams.of(paramString);

        assertThat(requestParams.findByKey("userId")).isEqualTo("1234");
    }


    @DisplayName("정상적인 request parameter 등록")
    @ParameterizedTest
    @CsvSource({"userId=test,1"
            , "userId=1234&password=4321,2"
            , "userId=1234&password=4321&email=1234@gmail.com,3"})
    void of(String parameterString, int keyCount) {
        //when
        HttpRequestParams reqParams = HttpRequestParams.of(parameterString);

        //then
        assertThat(reqParams.getKeys().size()).isEqualTo(keyCount);
    }

    @DisplayName("비정상 request parameter 오류")
    @ParameterizedTest
    @CsvSource({"userId=&password=4321"
            , "field1=&"
            , "=value1"
            , "field1==value1"
            , "&"})
    void newInstance(String parameterString) {
        assertThrows(IllegalArgumentException.class, () -> {
            HttpRequestParams httpRequestParams = HttpRequestParams.of(parameterString);
        });
    }
}