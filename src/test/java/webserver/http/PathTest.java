package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.RequestTestConstant.*;

public class PathTest {
    @Test
    @DisplayName("query String이 없는 path를 파싱하여 Path를 가져온다.")
    void parsingPathStringWithoutQueryStringAndGetPath() {
        // when
        Path path= new Path(PATH, new RequestParams());
        // then
        assertThat(path.path()).isEqualTo(PATH);
    }

    @Test
    @DisplayName("path를 파싱하여 query String이 분리 된 Path만을 가져온다.")
    void parsingProtocolVersionAndGetProtocol() {
        // when
        Path path= Path.create(PATH_QUERY_STRING);
        // then
        assertThat(path.path()).isEqualTo(PATH);
    }
}
