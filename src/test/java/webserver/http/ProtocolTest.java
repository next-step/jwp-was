package webserver.http;

import exception.InvalidProtocolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("HTTP Protocol 테스트")
class ProtocolTest {

    @Test
    @DisplayName("Protocol을 생성한다.")
    void create() {
        final String suite = "HTTP/1.1";

        final Protocol actual = new Protocol(suite);

        assertThat(actual.getType()).isEqualTo(Type.HTTP);
        assertThat(actual.getVersion()).isEqualTo(Version.VERSION1_1);
    }

    @Test
    @DisplayName("Protocol을 Type과 Version 을 정의하여 생성한다.")
    void create_by_type_version() {
        final Type type = Type.HTTP;
        final Version version = Version.VERSION1_1;

        final Protocol actual = new Protocol(type, version);

        assertThat(actual.toString()).isEqualTo("HTTP/1.1");
    }

    @Test
    @DisplayName("올바르지 않은 Protocol Suite이 입력될 경우 InvalidProtocolException이 발생한다.")
    void validate() {
        assertThatThrownBy(() -> new Protocol("http"))
                .isInstanceOf(InvalidProtocolException.class);
    }
}
