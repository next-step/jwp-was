package utils;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.HandlebarsUtils;

class HandlebarsUtilsTest {

    @DisplayName("view 이름으로 응답할 HTML 페이지를 찾는다.")
    @Test
    void getView() throws IOException, URISyntaxException {
        String view = HandlebarsUtils.getView("user/form", null);
        byte[] expectedBody = FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
        assertThat(view.getBytes(StandardCharsets.UTF_8)).isEqualTo(expectedBody);
    }
}
