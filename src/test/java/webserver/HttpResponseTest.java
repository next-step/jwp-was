package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.*;

public class HttpResponseTest {


    @Test
    @DisplayName("forward 테스트")
    void responseForward() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");
        assertThat(response.getHeaders().getValue("Content-Type")).isEqualTo(ContentType.HTML.getContent());
    }

    @Test
    @DisplayName("sendRedirect 테스트")
    void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
        assertThat(response.getHeaders().getValue("Location")).isEqualTo("/index.html");
    }


    @Test
    public void responseCookies() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
        assertThat(response.getHeaders().getValue("Location")).isEqualTo("/index.html");
        assertThat(response.getHeaders().getValue("Set-Cookie")).isEqualTo("logined=true");
        assertThat(response.getHeaders().getValue("Set-Cookie")).isNotEqualTo("logined=false");
    }

    private OutputStream createOutputStream(String fileName) throws FileNotFoundException {
        String testDirectory = "./src/test/resources/";
        return new FileOutputStream(testDirectory + fileName);
    }

}
