package http.request.requestline.path;

import http.request.querystring.QueryString;
import http.request.requestline.exception.RequestLineParsingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PathTest {

    @DisplayName("QueryString 있는 Path 생성하기")
    @Test
    void create() {
        /* given */
        String uriPath = "/users?key1=value1&key2=value2";

        /* when */
        Path path = new Path(uriPath);

        /* then */
        assertThat(path.getUri()).isEqualTo("/users");

        QueryString queryString = path.getQueryString();
        assertThat(queryString.size()).isEqualTo(2);
        assertThat(queryString.get("key1")).isEqualTo("value1");
        assertThat(queryString.get("key2")).isEqualTo("value2");
    }

    @DisplayName("QueryString 없는 Path 생성하기")
    @Test
    void create2() {
        /* given */
        String uriPath = "/users";

        /* when */
        Path path = new Path(uriPath);

        /* then */
        assertThat(path.getUri()).isEqualTo("/users");

        QueryString queryString = path.getQueryString();
        assertThat(queryString.size()).isEqualTo(0);
    }

    @DisplayName("Path 생성 시 null check하기")
    @Test
    void nullCheck() {
        /* when */ /* then */
        assertThrows(RequestLineParsingException.class, () -> new Path(null));
    }

    @DisplayName("QueryString을 분리하기 위한 ? 문자가 2개 이상인 경우 예외")
    @Test
    void illegalTokenSize() {
        /* given */
        String path = "/users?key1=value1?key2=value2";

        /* when */ /* then */
        assertThrows(RequestLineParsingException.class, () -> new Path(path));
    }
}