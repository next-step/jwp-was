package utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ParamsUtilsTest {
    @ParameterizedTest(name = "QueryString 파싱해서 각 속성값들을 확인하는 테스트")
    @ValueSource(strings = {
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net",
            "userId=javajigi&password=&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
    })
    void test_parsed_query(String query) {
        Map<String, String> params = ParamsUtils.parsedQueryString(query);
        System.out.println("params :" + params);
        assertThat(params).isNotNull();
    }
}