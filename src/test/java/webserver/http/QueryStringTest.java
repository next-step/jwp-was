package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.util.CollectionUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : yusik
 * @date : 2019-08-03
 */
public class QueryStringTest {

    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {
            "/users?userId=javajigi&password=password&name=JaeSung",
            "/users?userId=pplenty&password=test&name=kohyusik"})
    void 쿼리스트링_기본테스트(String requestURI) {
        QueryString queryString = QueryString.parse(requestURI);
        assertFalse(CollectionUtils.isEmpty(queryString.getParameterMap()));
    }

    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {
            "/users?name=&noValue=",
            "/users?userId=pplenty&password=&name=kohyusik&noValue="})
    void 쿼리스트링_value가_없는경우(String requestURI) {
        QueryString queryString = QueryString.parse(requestURI);
        Map paramMap = queryString.getParameterMap();
        assertEquals("", paramMap.get("noValue"));
    }

    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {
            "/users?name=&noValue",
            "/users?=213"})
    void 쿼리스트링_유효하지않은파라미터(String requestURI) {
        assertThrows(IllegalArgumentException.class, () -> QueryString.parse(requestURI));
    }
}
