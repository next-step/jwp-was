package http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import http.controller.DefaultController;
import http.controller.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created By kjs4395 on 2020-06-05
 */
public class ControllerHandlerTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("올바른 controller 반환하는지 테스트")
    void geUserControllerTest() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);

        assertThat(UserController.class.isInstance(ControllerHandler.getControllerProcess(request)));
    }

    @Test
    @DisplayName("디폴트 controller 반환하는지 테스트")
    void getDefaultTest() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET_CSS.txt"));
        HttpRequest request = new HttpRequest(in);

        assertThat(DefaultController.class.isInstance(ControllerHandler.getControllerProcess(request)));
    }
}
