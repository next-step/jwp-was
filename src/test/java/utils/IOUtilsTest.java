package utils;

import org.junit.jupiter.api.DisplayName;
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
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }

    @DisplayName("InputStream을 List<String>으로 변환한다.")
    @Test
    void readLines() throws IOException {
        String data = "GET /index.html HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive";
        InputStream in = new ByteArrayInputStream(data.getBytes());

        List<String> result = IOUtils.readLines(in);

        assertThat(result).containsExactly(
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive"
        );
    }
}
