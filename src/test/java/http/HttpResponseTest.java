package http;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void responseForward_html() throws Exception {
        // Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");
    }

    @Test
    public void responseForward_css() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward_CSS.txt"));
        response.forward("/styles.css");
    }

    @Test
    public void responseForward_js() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward_JS.txt"));
        response.forward("/scripts.js");
    }

    @Test
    public void responseForward_ttf() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward_FONT.txt"));
        response.forward("/glyphicons-halflings-regular.woff");
    }


    @Test
    public void responseRedirect() throws Exception {
        // Http_Redirect.txt 결과는 응답 headere에 Location 정보가 /index.html로 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.addHeader("Location", "/index.html");
        response.sendRedirect(HttpResponseCode.REDIRECT_300);
    }


    @Test
    public void responseCookies() throws Exception {
        // Http_Cookie.txt 결과는 응답 header에 Set-Cookie 값으로 logined=true 값이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.addHeader("Location", "/index.html");
        response.sendRedirect(HttpResponseCode.REDIRECT_300);

    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}