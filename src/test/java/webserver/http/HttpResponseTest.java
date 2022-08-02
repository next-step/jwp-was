package webserver.http;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @DisplayName("응답 객체에 쿠키를 추가할 수 있다.")
    @Test
    void addCookieTest() {
        // given
        Cookie loginCookie = new Cookie("logined", "true", "/");
        HttpResponse httpResponse = new HttpResponse();

        // when
        httpResponse.addCookie(loginCookie);

        // then
        assertThat(httpResponse.getHeaders().getValue("Set-Cookie")).isEqualTo("logined=true; Path=/");
    }

    @DisplayName("")
    static class OutputStreamTest {
        private static final String SRC_TEST_RESOURCES = "./src/test/resources";

        private static final String REDIRECT_RESPONSE_FILE = SRC_TEST_RESOURCES + "/Http_Redirect.txt";

        @BeforeEach
        void createTestFile() throws IOException {
            Path path = Path.of(REDIRECT_RESPONSE_FILE);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        }

        @AfterEach
        void clearTestFile() throws IOException {
//            Files.deleteIfExists(Path.of(REDIRECT_RESPONSE_FILE));
        }

        @Test
        void createWithOutputStream() throws Exception {
            // given
            OutputStream outputStream = new FileOutputStream(REDIRECT_RESPONSE_FILE);
            HttpResponse httpResponse = new HttpResponse(outputStream);

            // when
            httpResponse.sendRedirect("/index.html");

            // then
            String expectedResponse =
                    "HTTP/1.1 302 FOUND\n" +
                    "Location: /index.html\n" +
                    "\n";

            Assertions.assertThat(new File(REDIRECT_RESPONSE_FILE)).hasContent(expectedResponse);
        }
    }}
