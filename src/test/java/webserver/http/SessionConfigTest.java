package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("세션 설정")
class SessionConfigTest {

    @Test
    @DisplayName("생성 불가")
    void instance_thrownAssertionError() {
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> ReflectionUtils.newInstance(SessionConfig.class));
    }

    @Test
    @DisplayName("세션 쿠키 이름 조회")
    void sessionCookieName() {
        assertThat(SessionConfig.sessionCookieName()).isEqualTo("JSESSIONID");
    }
}
