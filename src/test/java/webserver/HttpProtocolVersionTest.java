package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpProtocolVersionTest {

    @Test
    void 프로토콜_버전_테스트() {
        String inputHttpProtocolVersion = "HTTP/1.1";
        HttpProtocolVersion httpProtocolVersion = new HttpProtocolVersion(inputHttpProtocolVersion);

        String[] splitProtocolVersion = inputHttpProtocolVersion.split("/");
        assertThat(httpProtocolVersion.getProtocol()).isEqualTo(splitProtocolVersion[0]);
        assertThat(httpProtocolVersion.getVersion()).isEqualTo(splitProtocolVersion[1]);
    }

    @Test
    void 잘못된_프로토콜_버전_테스트() {
        String inputHttpProtocolVersion = "HTTP1.1";

        assertThatThrownBy(
                () -> new HttpProtocolVersion(inputHttpProtocolVersion)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}