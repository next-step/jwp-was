package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProtocolTest {

    @Test
    void from() {
        assertThat(Protocol.from("HTTP/1.1"))
                .isEqualTo(Protocol.HTTP_1_1);
    }
}
