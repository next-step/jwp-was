package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.util.HttpResponseUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";
    private String mainDirectory = "./src/main/resources/";

    @DisplayName("Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.")
    @Test
    void responseForward() throws Exception {
        String indexData = HttpResponseUtil.getHttpResponse(getIndexData());
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");

        final String httpResponse = HttpResponseUtil.getHttpResponse(response.getBytes());

        assertThat(httpResponse).contains(indexData);

    }

    @DisplayName("Http_Redirect.txt 결과는 응답 header Location 정보가 /index.html로 포함되어 있어야 한다.")
    @Test
    void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");

        final String httpResponse = HttpResponseUtil.getHttpResponse(response.getBytes());

        assertThat(httpResponse).contains("Location: /index.html ");
    }

    @DisplayName("Http_Cookie.txt 결과는 응답 header에 Set-Cookie 값으로 logined=true 값이 포함되어 있어야 한다.")
    @Test
    void responseCookies() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");

        final String httpResponse = HttpResponseUtil.getHttpResponse(response.getBytes());

        assertAll(
                () -> assertThat(httpResponse).contains("HTTP/1.1 302 Found "),
                () -> assertThat(httpResponse).contains("Set-Cookie: logined=true"),
                () -> assertThat(httpResponse).contains("Location: /index.html")
        );
    }

    private byte[] getIndexData() throws IOException {
        File file = new File(mainDirectory + "/templates/index.html");
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return fileContent;
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}
