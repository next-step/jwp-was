package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class WebServerExecutorTest {

    @Test
    @DisplayName("서버를 실행할 때 args가 없는 경우 정상적으로 동작한다")
    void runServerEmptyArgs() {
        final String[] args = new String[]{};
        WebServerExecutor.execute(args);
    }

    @Test
    @DisplayName("서버를 실행할 때 args에 인자가 하나 있을 경우 포트 번호로 실행된다")
    void runServer() throws IOException {
        final String port = "8083";
        final String[] args = new String[]{port};

        WebServerExecutor.execute(args);

        assertThat(catchThrowable(() -> new ServerSocket(Integer.valueOf(port))))
                .isInstanceOf(BindException.class)
                .hasMessageContaining("Address already in use (Bind failed)");
    }

    @Test
    @DisplayName("입력한 args의 쓰레드풀 사이즈가 0보다 작을 경우 에러를 던진다")
    void IllegalThreadPoolArgument() {
        final String port = "8086";
        final String corePoolSize = "4";
        final String maximumPoolSize = "-1";
        final String[] args = new String[]{port, corePoolSize, maximumPoolSize};

        Throwable thrown = catchThrowable(() -> WebServerExecutor.execute(args));

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("입력한 args의 코어풀 사이즈가 0보다 작을 경우 에러를 던진다")
    void IllegalCorePoolArgument() {
        final String port = "8087";
        final String corePoolSize = "-1";
        final String maximumPoolSize = "4";
        final String[] args = new String[]{port, corePoolSize, maximumPoolSize};

        Throwable thrown = catchThrowable(() -> WebServerExecutor.execute(args));

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class);
    }


}