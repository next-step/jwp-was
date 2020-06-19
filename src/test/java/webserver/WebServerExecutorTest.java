package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testUtils.PortNumberProvider;

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
    @DisplayName("서버를 실행할 때 args에 인자가 하나 있을 경우 포트 번호로 실행한다")
    void runServerPortArgs() {
        final String port = PortNumberProvider.fetchPortNumberStr();
        final String[] args = new String[]{port};

        WebServerExecutor.execute(args);

        assertThat(catchThrowable(() -> new ServerSocket(Integer.valueOf(port))))
                .isInstanceOf(BindException.class)
                .hasMessageContaining("Address already in use (Bind failed)");
    }


    @Test
    @DisplayName("서버를 실행할 때 args에 포트, 스레드풀 설정을 할 수 있다")
    void runServerPortThreadPoolArgs() {
        final String port = PortNumberProvider.fetchPortNumberStr();
        final String corePoolSize = "4";
        final String maximumPoolSize = "10";
        final String keepAliveTime = "10";
        final String maximumQueueSize = "10";

        final String[] args = new String[]{port, corePoolSize, maximumPoolSize, keepAliveTime, maximumQueueSize};

        WebServerExecutor.execute(args);

        assertThat(catchThrowable(() -> new ServerSocket(Integer.valueOf(port))))
                .isInstanceOf(BindException.class)
                .hasMessageContaining("Address already in use (Bind failed)");
    }

    @Test
    @DisplayName("서버를 실행할 때 args의 쓰레드풀 사이즈가 0보다 작을 경우 에러를 던진다")
    void IllegalThreadPoolArgument() {
        final String port = PortNumberProvider.fetchPortNumberStr();
        final String corePoolSize = "4";
        final String maximumPoolSize = "-1";
        final String[] args = new String[]{port, corePoolSize, maximumPoolSize};

        Throwable thrown = catchThrowable(() -> WebServerExecutor.execute(args));

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("서버를 실행할 때 args의 코어풀 사이즈가 0보다 작을 경우 에러를 던진다")
    void IllegalCorePoolArgument() {
        final String port = PortNumberProvider.fetchPortNumberStr();
        final String corePoolSize = "-1";
        final String maximumPoolSize = "4";
        final String[] args = new String[]{port, corePoolSize, maximumPoolSize};

        Throwable thrown = catchThrowable(() -> WebServerExecutor.execute(args));

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class);
    }


}