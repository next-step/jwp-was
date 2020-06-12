package webserver;

import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    public void responseForward() throws Exception {
        // Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");

        String location = "/index.html";
        assertThat(readFileWithContainString(testDirectory + "Http_Forward.txt", location)).isEqualTo(true);
    }

    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");

        String location = "Location: /index.html";
        assertThat(readFileWithContainString(testDirectory + "Http_Redirect.txt", location)).isEqualTo(true);
    }

    @Test
    public void responseCookies() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");

        String location = "Set-Cookie: logined=true";
        assertThat(readFileWithContainString(testDirectory + "Http_Cookie.txt", location)).isEqualTo(true);
    }

    public boolean readFileWithContainString(String filePath, String msg) throws Exception {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();

        br = new BufferedReader(new FileReader(filePath));

        String string = br.lines()
                .filter(s -> {
                    System.out.println(s);
                    return s.contains(msg);
                })
                .findFirst().orElse(null);


        return StringUtils.isEmpty(string) ? false : true;
    }

    private OutputStream createOutputStream(String fileName) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + fileName));
    }
}
