package study;

import org.junit.jupiter.api.Test;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UriComponentsBuilderTest {

    @Test
    void request_path_를_쿼리맵으로_변환한다() {
        String requestPath = "/test?param1=ab&param2=cd&param2=ef";

        MultiValueMap<String, String> parameters =
                UriComponentsBuilder.fromUriString(requestPath).build().getQueryParams();

        List<String> param1 = parameters.get("param1");
        List<String> param2 = parameters.get("param2");

        assertThat(param1.contains("ab")).isTrue();
        assertThat(param2.contains("cd")).isTrue();
        assertThat(param2.contains("ef")).isTrue();
        assertThat(param2.size()).isEqualTo(2);
        assertThat(param1.size()).isEqualTo(1);
    }

    @Test
    void requestBody_를_쿼리맵으로변환() {
        String requestBody = "?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        MultiValueMap<String, String> parameters =
                UriComponentsBuilder.fromUriString(requestBody).build().getQueryParams();

        List<String> param1 = parameters.get("userId");
        List<String> param2 = parameters.get("password");

        assertThat(param1.contains("javajigi")).isTrue();
        assertThat(param2.contains("password")).isTrue();
    }
}
