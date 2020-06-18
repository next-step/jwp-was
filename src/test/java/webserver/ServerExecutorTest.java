package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerExecutorTest {

    @Test
    @DisplayName("서버를 정상적으로 실행할 수 있다")
    void runServer() {
        final String[] args = new String[]{};
        ServerExecutor.execute(args);
    }

}