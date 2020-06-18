package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WebServerExecutorTest {

    @Test
    @DisplayName("서버를 정상적으로 실행할 수 있다")
    void runServer() {
        final String[] args = new String[]{"8083"};
        WebServerExecutor.execute(args);
    }

}