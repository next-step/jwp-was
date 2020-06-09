package http.response;

import http.common.HeaderField;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseHeaderTest {

    @Test
    void createResponseHeader() {
        new ResponseHeader();
    }

    @Test
    void addTest() {
        final String name = "Content-Type";
        final String value = "text/html";
        final HeaderField headerField = new HeaderField(name, value);
        final ResponseHeader responseHeader = new ResponseHeader();

        responseHeader.addHeader(headerField);

        assertThat(responseHeader.getValue(name)).isEqualTo(value);
    }

    @Test
    void getTest() {
        final String name = "Content-Type";
        final String value = "text/html";
        final HeaderField headerField = new HeaderField(name, value);
        final ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.addHeader(headerField);

        final String result = responseHeader.getValue(name);

        assertThat(result).isEqualTo(value);
    }

    @Test
    void returnEmpty() {
        final ResponseHeader responseHeader = new ResponseHeader();
        final String name = "Content-Type";
        final String expected = "";

        final String result = responseHeader.getValue(name);

        assertThat(result).isEqualTo(expected);
    }

}