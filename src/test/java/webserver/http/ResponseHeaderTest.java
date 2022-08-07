package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseHeaderTest {

    @Test
    void 응답헤더_테스트() {
        String key = "TEST_KEY";
        String value = "TEST_VALUE";
        ResponseHeader header = new ResponseHeader();
        header.addHeader(key, value);

        assertThat(header.getHeader(key)).isEqualTo(value);
        assertThat(header.getHeaderKeys()).hasSize(1);
        assertThat(header.getHeaderKeys()).contains(key);
    }

    @Test
    void 응답헤더_상태_테스트() {
        ResponseHeader header = new ResponseHeader();

        assertThat(header.getStatusMessage()).isEqualTo(HttpStatus.OK.toString());
    }

    @Test
    void 응답헤더_상태변경_테스트() {
        ResponseHeader header = new ResponseHeader();
        header.updateStatus(HttpStatus.FOUND);
        assertThat(header.getStatusMessage()).isEqualTo(HttpStatus.FOUND.toString());
    }
}
