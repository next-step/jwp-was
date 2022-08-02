package webserver.request.domain.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.exception.NotFoundMethod;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MethodTest {

    @Test
    @DisplayName("Method 에 포함되지 않는 method 가 들어오면 예외를 던진다.")
    public void methodExceptionTest() {
        assertThrows(NotFoundMethod.class, () -> {
            Method.from("GETT");
        });
    }
}
