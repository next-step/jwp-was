package webserver.http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringTest {

    @Test
    void testParse() {
        String queryString = "userId=javajigi&password=password&name=JaeSung";
        Map<String, String> queryParameter = Map.of(
                "userId", "javajigi",
                "password", "password",
                "name", "JaeSung"
        );

        assertThat(Stream.of(StringUtils.split(queryString, "&"))
                .map(keyValue -> StringUtils.split(keyValue, "="))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]))).isEqualTo(queryParameter);
    }

    @DisplayName("정상적인 QueryString 이 들어왔을 때")
    @Test
    void getQueryString() {
        assertThat(new QueryString("userId=javajigi&password=password&name=JaeSung")).isEqualTo(new QueryString("userId", "javajigi", "password", "password", "name", "JaeSung"));
    }

}