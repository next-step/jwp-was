package webserver.httprequest.requestline;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class HttpProtocolSchemaTest {

    @Test
    void create_and_equals() {
        HttpProtocolSchema httpProtocolSchemaOne = new HttpProtocolSchema("HTTP/1.0");
        HttpProtocolSchema httpProtocolSchemaOnePointOne = new HttpProtocolSchema("HTTP/1.1");

        assertThat(httpProtocolSchemaOne.getProtocol()).isEqualTo("HTTP");
        assertThat(httpProtocolSchemaOne.getVersion()).isEqualTo(HttpVersion.ONE);

        assertThat(httpProtocolSchemaOnePointOne.getProtocol()).isEqualTo("HTTP");
        assertThat(httpProtocolSchemaOnePointOne.getVersion()).isEqualTo(HttpVersion.ONE_POINT_ONE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"HTTP1.0", "HTTP1.1", "HTTP", "1.0", "1.1", " ", "", "HTTP/1.0/1", "HTTP/1.1/1"})
    void create_exception(String rawHttpProtocol) {
        assertThrows(IllegalArgumentException.class, () -> new HttpProtocolSchema(rawHttpProtocol));
    }
}
