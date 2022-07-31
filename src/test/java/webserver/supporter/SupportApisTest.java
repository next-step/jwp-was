package webserver.supporter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.servlet.Controller;
import webserver.servlet.UserCreateController;

class SupportApisTest {

    @ParameterizedTest
    @CsvSource(value = {"/user/create"})
    void isSupportedTest(String supportedApi) {
        assertThat(SupportApis.isSupported(supportedApi)).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"/user/create1", "/test/notSupported", "/"})
    void notSupportedTest(String notSupportedApi) {
        assertThat(SupportApis.isSupported(notSupportedApi)).isFalse();
    }

    @Test
    void getServletTest() {
        Controller controller = SupportApis.getServlet("/user/create");

        assertThat(controller)
            .isInstanceOf(UserCreateController.class);
    }

}
