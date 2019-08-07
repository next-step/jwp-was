package webserver.http.response;

import org.junit.jupiter.api.Test;
import webserver.http.Cookie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

class HttpResponseTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    void responseRedirect() throws Exception {
        // Http_Redirect.txt 결과는 응답 headere에 Location 정보가 /index.html로 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.redirect("/index");
    }

    @Test
    void responseCookies() throws Exception {
        // Http_Cookie.txt 결과는 응답 header에 Set-Cookie 값으로 logined=true 값이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addCookie(new Cookie("logined", "true"));
        response.redirect("/index");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}