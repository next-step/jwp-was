package enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpStatusTest {

    @Test
    @DisplayName("HttpStatus를 파리미터로 입력시 해당 HttpStatus의 Code를 반환한다.")
    void getHttpCodeTest() {
        assertThat(HttpStatus.getHttpCode(HttpStatus.OK)).isEqualTo(200);
    }
}