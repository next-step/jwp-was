package response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class HttpResponseTest {

    private String testDirectory = "./src/test/resources/response/";

    @Test
    @DisplayName("Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.")
    public void responseForward() throws Exception {
        HttpResponse response = HttpResponse.parse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");
    }

    @Test
    @DisplayName("Http_Redirect.txt 결과는 응답 headere에 Location 정보가 /index.html로 포함되어 있어야 한다.")
    public void responseRedirect() throws Exception {
        HttpResponse response = HttpResponse.parse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
    }

    @Test
    @DisplayName("Http_Cookie.txt 결과는 응답 header에 Set-Cookie 값으로 logined=true 값이 포함되어 있어야 한다.")
    public void responseCookies() throws Exception {
        HttpResponse response = HttpResponse.parse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }

}
