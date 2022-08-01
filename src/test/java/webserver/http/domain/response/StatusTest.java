package webserver.http.domain.response;

import org.junit.jupiter.api.Test;
import webserver.http.domain.Protocol;

import static org.assertj.core.api.Assertions.assertThat;

class StatusTest {

    @Test
    void ok() {
        Status actual = Status.ok();
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new Status(Protocol.HTTP_1_1, StatusCode.OK));
    }

    @Test
    void found() {
        Status actual = Status.found();
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new Status(Protocol.HTTP_1_1, StatusCode.FOUND));
    }

    @Test
    void badRequest() {
        Status actual = Status.badRequest();
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new Status(Protocol.HTTP_1_1, StatusCode.BAD_REQUEST));
    }

    @Test
    void notFound() {
        Status actual = Status.notFound();
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new Status(Protocol.HTTP_1_1, StatusCode.NOT_FOUND));
    }

    @Test
    void internalError() {
        Status actual = Status.internalError();
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new Status(Protocol.HTTP_1_1, StatusCode.INTERNAL_ERROR));
    }
}