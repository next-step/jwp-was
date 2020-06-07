package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {
    @DisplayName("RequestLine에서 URL/QueryString 추출")
    @Test
    void getPathAndQueries() throws UnsupportedEncodingException {
        //given
        String requestLine = "GET /user/create?name=seung&email=email@gmail.com HTTP/1.1";

        //when
        String url = Path.getUrl(requestLine);
        Map<String, String> queries = Path.getQueries(requestLine);

        //then
        assertThat(url).isEqualTo("/user/create");
        assertThat(queries.get("name")).isEqualTo("seung");
        assertThat(queries.get("email")).isEqualTo("email@gmail.com");
    }
}
