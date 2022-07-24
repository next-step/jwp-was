package webserver.http.response;

import org.junit.jupiter.api.Test;
import webserver.http.HttpProtocolSchema;
import webserver.http.HttpVersion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpResponseStatusLineTest {

    @Test
    void rawStatusLine() {
        assertAll(
                () -> assertThat(new HttpResponseStatusLine(HttpProtocolSchema.of(HttpVersion.ONE), HttpStatus.OK).rawStatusLine())
                        .isEqualTo("HTTP/1.0 200 OK"),
                () -> assertThat(new HttpResponseStatusLine(HttpProtocolSchema.of(HttpVersion.ONE), HttpStatus.NOT_FOUND).rawStatusLine())
                        .isEqualTo("HTTP/1.0 404 Not Found"),
                () -> assertThat(HttpResponseStatusLine.fromOnePointOne(HttpStatus.OK).rawStatusLine())
                        .isEqualTo("HTTP/1.1 200 OK"),
                () -> assertThat(HttpResponseStatusLine.fromOnePointOne(HttpStatus.NOT_FOUND).rawStatusLine())
                        .isEqualTo("HTTP/1.1 404 Not Found")
        );
    }
}
