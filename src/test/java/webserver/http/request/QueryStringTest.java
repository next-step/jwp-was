package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : yusik
 * @date : 2019-08-03
 */
@DisplayName("쿼리스트링 테스트")
public class QueryStringTest {

    @DisplayName("기본 테스트")
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {
            "userId=javajigi&password=password&name=JaeSung",
            "userId=pplenty&password=test&name=kohyusik"})
    void defaultTest(String queryStringInput) {
        QueryString queryString = QueryString.parse(queryStringInput);
        assertFalse(queryString.getParameters().isEmpty());
    }

    @DisplayName("유효하지않은 파라미터")
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {
            "name=&noValue=",
            "name=&noValue",
            "=213"})
    void exceptionTest(String queryStringInput) {
        assertThrows(IllegalArgumentException.class, () -> QueryString.parse(queryStringInput));
    }
}
