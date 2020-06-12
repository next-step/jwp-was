package http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpHeadersTest {

    @ParameterizedTest
    @DisplayName("헤더가 정상적으로 생성되는지 확인한다.")
    @ValueSource(strings = {"Host: localhost:8080", "Connection: keep-alive", "Content-Length: 59"})
    void createHeaderTest(String line) {
        String[] values = line.split(": ");

        HttpHeaders headers = new HttpHeaders();
        headers.addHeader(line);
        assertThat(headers.getHeader(values[0])).isEqualTo(values[1]);
    }

    @ParameterizedTest
    @DisplayName("유효하지 않은 값으로 헤더 생성 시에 적절한 예외를 일으키는지 확인한다.")
    @ValueSource(strings = {"", "aaaa", "a:b", "a: b: c"})
    void createInvalidHeaderTest(String line) {
        HttpHeaders headers = new HttpHeaders();
        assertThatIllegalArgumentException().isThrownBy(() -> {
            headers.addHeader(line);
        }).withMessage(HttpHeaders.ILLEGAL_HEADER);

    }
}
