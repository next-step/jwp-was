package webserver.http.model.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

class HttpResponseTest {

    @DisplayName("HttpResponse를 forward한 응답 결과를 Http_Forward.txt에 기재하며, 응답 body에 index.html이 포함되어 있어야 한다.")
    @Test
    public void responseForward() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");
    }

    @DisplayName("HttpResponse를 Redirect한 응답 결과를 Http_Redirect.txt에 기재하며, 응답 header에 Location 정보가 /index.html로 포함되어 있어야 한다.")
    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
    }

    @DisplayName("HttpResponse에 cookie 값을 설정한 응답 결과를 Http_Cookie.txt에 기재하며, 응답 header에 Set-Cookie 값으로 logined=true 값이 포함되어 있어야 한다.")
    @Test
    public void responseCookies() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        String testDirectory = "./src/test/resources/";
        return new FileOutputStream(testDirectory + filename);
    }
}