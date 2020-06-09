package http;

import http.enums.HttpResponseCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpResponseTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("response code 200 ok")
    public void responseForward() throws Exception {
        // Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(new DataOutputStream(createOutputStream("Http_Forword.txt")));
        response.forword("/index.html");

        assertEquals(HttpResponseCode.OK, response.getResponseCode());
    }

    @Test
    @DisplayName("response code 302 redirect")
    public void responseRedirect() throws Exception {
        // Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(new DataOutputStream(createOutputStream("Http_Forword.txt")));
        response.sendRedirect("/index.html");

        assertEquals(HttpResponseCode.REDIRECT, response.getResponseCode());
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}
