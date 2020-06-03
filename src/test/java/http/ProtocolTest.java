package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ProtocolTest {

    @Test
    @DisplayName("생성")
    void create() {
        Protocol protocol = new Protocol("HTTP/1.1");
        assertThat(protocol).isEqualTo(new Protocol("HTTP/1.1"));
    }

    @Test
    @DisplayName("유효성 검사")
    void validate() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Protocol("HTTP"));
    }
}
