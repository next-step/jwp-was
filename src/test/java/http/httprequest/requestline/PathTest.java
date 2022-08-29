package http.httprequest.requestline;

import http.httprequest.requestline.RequestParams;
import http.httprequest.requestline.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {

    @Test
    @DisplayName("from 메서드가 정상적으로 객체를 생성해주는지 확인")
    void create() {
        RequestParams requestParams = RequestParams.from("userId=javajigi&password=password&name=JaeSung");
        Path expected = new Path("/users", requestParams);

        Path result = Path.from("/users?userId=javajigi&password=password&name=JaeSung");

        assertThat(result).isEqualTo(expected);
    }
}
