package webserver.http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProtocolTest {
    @Test
    void 프로토콜_파싱() {
        String expected = "HTTP";
        Protocol protocol = new Protocol(expected, new Version("2"));

        String actual = protocol.value();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 프로토콜_버전_파싱() {
        String expected = "2";
        Protocol protocol = new Protocol("HTTP", new Version(expected));

        Version actual = protocol.getVersion();

        assertThat(actual).isEqualTo(new Version(expected));
    }
}
