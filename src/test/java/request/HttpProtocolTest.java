package request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpProtocolTest {

    @Test
    @DisplayName("Protocol과 version이 들어있는 HttpProtocol를 반환합니다.")
    void httpProtocolInstanceTest() {
        String httpProtocolValue = "HTTP/1.1";

        HttpProtocol httpProtocol = HttpProtocol.Instance(httpProtocolValue);
        HttpProtocol 비교값 = new HttpProtocol("HTTP", "1.1");

        assertThat(httpProtocol).isEqualTo(비교값);
    }
}
