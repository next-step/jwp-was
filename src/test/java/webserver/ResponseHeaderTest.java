package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseHeaderTest {

    @DisplayName("ResponseHeader 생성")
    @Test
    void of() {

        // given
        String name = "Content-Type";
        String[] values = {"text/html", "charset=utf-8"};

        // when
        ResponseHeader responseHeader = ResponseHeader.of(name, values);

        // then
        assertThat(responseHeader).isEqualTo(new ResponseHeader(name, Arrays.asList(values)));
    }
}