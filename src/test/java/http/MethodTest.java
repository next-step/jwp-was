package http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MethodTest {
    private Method sut;

    @Test
    public void test_find() {
        // given
        String method = "get";

        // when
        Method get = sut.find(method);

        // then
        assertEquals(Method.GET, get);
    }

}