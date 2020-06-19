package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import testUtils.PortNumberProvider;

import java.net.BindException;
import java.net.ServerSocket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class WebServerArgumentTest {

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

    @DisplayName("서버를 실행할 때 포트가 사용 가능한 범위를 벗어난 경우 예외를 던진다")
    @ParameterizedTest
    @ValueSource(strings = {"-1","65536"})
    void portOutOfRange(String port) {
        final String[] args = {port};

        final Throwable thrown = catchThrowable(() -> WebServer.main(args));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Port value out of range: " + port);
    }

    @Test
    @DisplayName("서버를 실행할 때 해당 포트를 이미 사용중인 경우 예외를 던진다")
    void aleadyUsingPort() {
        final String port = PortNumberProvider.fetchPortNumberStr();
        final String[] args = new String[]{port};

        WebServerExecutor.execute(args);
        final Throwable thrown = catchThrowable(() -> WebServer.main(args));

        assertThat(thrown)
                .isInstanceOf(BindException.class)
                .hasMessageContaining("Address already in use (Bind failed)");
    }

    @DisplayName("서버를 실행할 때 args로 받은 최대 쓰레드풀 크기 값이 유효하지 않을 경우 예외를 던진다")
    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "5하나", "5a"})
    void illegalMaximumPoolSize(String maximumPoolSize) {
        final String[] args = new String[]{null, null, maximumPoolSize, null, null};

        final Throwable thrown = catchThrowable(() -> WebServer.main(args));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("서버를 실행할 때 args로 받은 코어풀 크기 값이 유효하지 않을 경우 예외를 던진다")
    @ParameterizedTest
    @ValueSource(strings = {"-1", "5하나", "5a"})
    void illegalCorePoolSize(String corePoolSize) {
        final String[] args = new String[]{null, corePoolSize, null, null, null};

        final Throwable thrown = catchThrowable(() -> WebServer.main(args));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class);
    }

}
