package webserver.http;

import http.httprequest.requestline.Params;
import http.httprequest.requestline.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {

    @Test
    @DisplayName("from 메서드가 정상적으로 객체를 생성해주는지 확인")
    void create() {
        Params params = Params.from("userId=javajigi&password=password&name=JaeSung");
        Path expected = new Path("/users", params);

        Path result = Path.from("/users?userId=javajigi&password=password&name=JaeSung");

        assertThat(result).isEqualTo(expected);
    }
}
