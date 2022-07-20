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
}
