package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.domain.Parameters;
import webserver.domain.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {

    @DisplayName("쿼리스트링이 없는 패스를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"/users", "/account/v1/order"})
    void createPathWithoutQueryString(String path) {
        Path actualPath = new Path(path);

        assertThat(actualPath).isEqualTo(new Path(path));
    }
}
