package http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseHeadersTest {
    private ResponseHeaders sut;

    @Test
    void makeHeaderString() {
        // given
        sut = new ResponseHeaders();
        String headerKey1 = "content-type";
        String headerValue1 = "text/html; charset=UTF";
        String headerKey2 = "content-length";
        String headerValue2 = "418";
        String headerKey3 = "cache-control";
        String headerValue3 = "no-cache, no-store, must-revalidate";

        String rawResponseHeader = headerKey1 + ": " + headerValue1 + "\r\n"
                + headerKey2 + ": " + headerValue2 + "\r\n"
                + headerKey3 + ": " + headerValue3 + "\r\n";

        sut.put(headerKey1, headerValue1);
        sut.put(headerKey2, headerValue2);
        sut.put(headerKey3, headerValue3);

        // when
        String headersString = sut.makeHeadersString();

        // then
        assertEquals(rawResponseHeader, headersString);

    }

    @Test
    void makeEmptyResponseHeaders() {
        // given
        sut = ResponseHeaders.makeEmptyResponseHeaders();

        // when
        String headersString = sut.makeHeadersString();

        // then
        assertEquals("", headersString);
    }
}