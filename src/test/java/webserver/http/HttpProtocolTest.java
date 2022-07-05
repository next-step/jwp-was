package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpProtocolTest {


    @DisplayName("Protocol 검증")
    @Test
    void protocolParser() {

        HttpProtocol httpProtocol = new HttpProtocol(RequestFixture.PROTOCOL);

        then(httpProtocol.getProtocol()).isNotEmpty();
        then(httpProtocol.getProtocol()).isEqualTo(HttpProtocolFixture.PROTOCOL);
        then(httpProtocol.getVersion()).isEqualTo(HttpProtocolFixture.VERSION);
    }

    @DisplayName("잘못된 Protocol 데이터 처리")
    @Test()
    void missProtocolString() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new HttpProtocol(HttpProtocolFixture.PROTOCOL)
        );
    }
}
