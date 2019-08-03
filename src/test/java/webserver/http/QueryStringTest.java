package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.util.CollectionUtils;

import java.util.Map;

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
            "/users?userId=javajigi&password=password&name=JaeSung",
            "/users?userId=pplenty&password=test&name=kohyusik"})
    void defaultTest(String requestURI) {
        QueryString queryString = QueryString.parse(requestURI);
        assertFalse(CollectionUtils.isEmpty(queryString.getParameterMap()));
    }

    @DisplayName("value가 \"\"인 경우")
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {
            "/users?name=&noValue=",
            "/users?userId=pplenty&password=&name=kohyusik&noValue="})
    void noValueTest(String requestURI) {
        QueryString queryString = QueryString.parse(requestURI);
        Map paramMap = queryString.getParameterMap();
        assertEquals("", paramMap.get("noValue"));
    }

    @DisplayName("유효하지않은 파라미터")
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {
            "/users?name=&noValue",
            "/users?=213"})
    void exceptionTest(String requestURI) {
        assertThrows(IllegalArgumentException.class, () -> QueryString.parse(requestURI));
    }
}
