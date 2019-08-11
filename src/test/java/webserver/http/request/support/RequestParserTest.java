package webserver.http.request.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;
import webserver.http.request.ParameterMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : yusik
 * @date : 2019-08-10
 */
@DisplayName("요청 헤더 파싱")
public class RequestParserTest {

    @DisplayName("기본 테스트")
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {
            "/users/123?test=1",
            "/users/52512?limit=0",
    })
    void defaultTest(String requestUri) {
        String path = RequestParser.parsePathFromRequestURI(requestUri);
        assertTrue(StringUtils.isNotBlank(path));
    }

    @DisplayName("기본 테스트")
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {
            "userId=javajigi&password=password&name=JaeSung",
            "userId=pplenty&password=test&name=kohyusik"})
    void queryDefaultTest(String queryStringInput) {
        ParameterMap parameters = RequestParser.parseQuery(queryStringInput);
        assertFalse(parameters.isEmpty());
    }

    @DisplayName("유효하지않은 파라미터")
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {
            "name=&noValue=",
            "name=&noValue",
            "=213"})
    void queryExceptionTest(String queryStringInput) {
        assertThrows(IllegalArgumentException.class, () -> RequestParser.parseQuery(queryStringInput));
    }
}
