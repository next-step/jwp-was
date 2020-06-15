package http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseBodyTest {
    private ResponseBody sut;
    @Test
    void new_responseBody() {
        // given
        sut = new ResponseBody("testsetsetset".getBytes());

        // when
        String s = new String(sut.getMessageBodyByteArray());

        // then
        assertEquals("testsetsetset", s);
    }

}