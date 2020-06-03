package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class ProtocolTest {

    @Test
    void createTest() {
        Protocol protocol = new Protocol("HTTP/1.1");
        assertThat(protocol.getProtocol()).isEqualTo("HTTP");
        assertThat(protocol.getVersion()).isEqualTo("1.1");
        assertThat(protocol).isEqualTo(new Protocol("HTTP/1.1"));
    }
}
