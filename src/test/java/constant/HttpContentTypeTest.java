package constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpContentTypeTest {

    @Test
    @DisplayName("index.html라는 template을 주었을 때, 올바른 contentType을 반환합니다.")
    void correctContentType() {
        String index = "index.html";

        HttpContentType contentType = HttpContentType.of(index);

        assertThat(contentType.equals(HttpContentType.TEXT_HTML));
    }

    @Test
    @DisplayName("정의 되지 않은 contentType을 찾을려고 할때 에러를 반환합니다.")
    void notFoundContentType() {
        String typescript = "index.ts";

        assertThatIllegalArgumentException().isThrownBy(() -> {
            HttpContentType.of(typescript);
        });
    }

}
