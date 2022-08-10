package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        String data = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }

    @Test
    void header_리스트만들기() throws UnsupportedEncodingException {
        String data = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        final InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        List<String> headers = IOUtils.readHeaderData(br);

        assertThat(headers.size()).isEqualTo(6);
    }

    @Test
    void body_가져오기() throws IOException {
        String data = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
        "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        final InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        IOUtils.readHeaderData(br);
        final String content = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        final String s = IOUtils.readData(br, content.getBytes().length);

        assertThat(s).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }
}
