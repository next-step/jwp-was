package http.request.method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpMethodTest {

    @DisplayName("GET method인지 체크하기")
    @ParameterizedTest
    @CsvSource(value = {"GET:true", "POST:false", "PUT:false", "DELETE:false"}, delimiter = ':')
    void isGet(HttpMethod httpMethod, boolean result) {
        /* when */
        boolean expected = httpMethod.isGet();

        /* then */
        assertEquals(expected, result);
    }

    @DisplayName("POST method인지 체크하기")
    @ParameterizedTest
    @CsvSource(value = {"GET:false", "POST:true", "PUT:false", "DELETE:false"}, delimiter = ':')
    void isPost(HttpMethod httpMethod, boolean result) {
        /* when */
        boolean expected = httpMethod.isPost();

        /* then */
        assertEquals(expected, result);
    }
}
