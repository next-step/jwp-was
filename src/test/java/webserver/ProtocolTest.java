package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProtocolTest {

    @Test
    void create() {
        assertThat(Protocol.findByAlias("HTTP/1.1"))
                .isEqualTo(Protocol.HTTP_1_1);
    }
}
