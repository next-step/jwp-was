package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import testUtils.PortNumberProvider;

import java.net.BindException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class WebServerIllegarArgumentTest {

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
