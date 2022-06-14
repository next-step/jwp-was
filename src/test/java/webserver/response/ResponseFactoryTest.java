package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseFactoryTest {

    @DisplayName("Unauthorized 일 경우 401 을 응답해야 한다.")
    @Test
    void createUnauthorized() {
        String actual = new String(ResponseFactory.createUnauthorized("Password Mismatch").toBytes());
        String expected = "HTTP/1.1 401 Unauthorized \r\n"
                + "Content-Type: text/html;charset=utf-8 \r\n"
                + "Content-Length: 17 \r\n"
                + "\r\n"
                + "Password Mismatch";
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Not Found 일 경우 404 를 응답해야 한다.")
    @Test
    void createNotFound() {
        String actual = new String(ResponseFactory.createNotFound("User Not Found").toBytes());
        String expected = "HTTP/1.1 404 Not Found \r\n"
                + "Content-Type: text/html;charset=utf-8 \r\n"
                + "Content-Length: 14 \r\n"
                + "\r\n"
                + "User Not Found";
        assertThat(actual).isEqualTo(expected);
    }


    @DisplayName("Not Implemented 일 경우 501 을 응답해야 한다.")
    @Test
    void createNotImplemented() {
        String actual = new String(ResponseFactory.createNotImplemented().toBytes());
        String expected = "HTTP/1.1 501 Not Implemented \r\n"
                + "Content-Type: text/html;charset=utf-8 \r\n"
                + "Content-Length: 19 \r\n"
                + "\r\n"
                + "Not Implemented Yet";
        assertThat(actual).isEqualTo(expected);
    }
}
