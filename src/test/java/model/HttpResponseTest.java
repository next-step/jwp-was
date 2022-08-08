package model;

import org.junit.jupiter.api.Test;
import utils.IOUtils;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {

    @Test
    void 리다이렉트_응답() throws UnsupportedEncodingException {

        String location = "/";

        final HttpResponse redirect = HttpResponse.redirect(location, createHttpHeader());

        assertThat(redirect.getMessages().get(0)).isEqualTo("HTTP/1.1 302 OK \r\n");
    }

    private HttpHeader createHttpHeader() throws UnsupportedEncodingException {
        String data = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "Cookie: logined=true\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        final InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        return new HttpHeader(IOUtils.readHeaderData(br));
    }

}
