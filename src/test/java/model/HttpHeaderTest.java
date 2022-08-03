package model;

import org.junit.jupiter.api.Test;
import utils.IOUtils;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeaderTest {

    @Test
    void 헤더생성() throws UnsupportedEncodingException {
        final HttpHeader httpHeader = createHttpHeader();

        assertThat(httpHeader.getContentLength()).isEqualTo(59);
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

    @Test
    void 쿠키값_가져오기() throws UnsupportedEncodingException {
        final HttpHeader httpHeader = createHttpHeader();

        assertThat(httpHeader.getCookie()).isEqualTo("logined=true");
    }
}
