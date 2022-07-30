package webserver.session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UUIDSessionIdGeneratorTest {

    @DisplayName("매번 다른 세션 ID를 생성한다(중복 확률은 아주 희박하다)")
    @Test
    void generate_random_session_id() {
        final SessionIdGenerator generator = new UUIDSessionIdGenerator();

        final String actual = generator.generate();

        assertThat(actual).isNotEqualTo(generator.generate());
    }
}
