package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {

    @Test
    void helloWorld() {
        // given
        final var socket = new StubSocket();
        final var requestHandler = new RequestHandler(socket);

        // when
        requestHandler.run();
        final var actual = socket.output();

        // then
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8",
                "Content-Length: 11",
                "",
                "Hello World");

        assertThat(actual).isEqualTo(expected);
    }
}
