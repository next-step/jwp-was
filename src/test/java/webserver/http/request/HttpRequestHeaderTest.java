package webserver.http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestHeaderTest {
    private String reqHeader = "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Cache-Control: max-age=0\n" +
            "Upgrade-Insecure-Requests: 1\n" +
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\n" +
            "Accept-Encoding: gzip, deflate, br\n" +
            "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
            "Cookie: Idea-efa8df2d=1bb5567d-7139-475d-a323-ee4e3492e089; Idea-dcb08711=75f15d7b-5df3-4803-abcc-3cc01d84def2";

    private BufferedReader br;
    private HttpRequestHeader requestHeader;

    @BeforeEach
    void setUp() throws Exception {
        br = new BufferedReader
                (new InputStreamReader(
                        new ByteArrayInputStream(reqHeader.getBytes())));

        requestHeader = HttpRequestHeader.of(br);
    }

    @DisplayName("httpRequestHeader 생성")
    @Test
    void createRequestHeader() throws IOException {
        //then
        assertThat(requestHeader.findByKey("Connection")).isEqualTo("keep-alive");
        assertThat(requestHeader.getKeys().size()).isEqualTo(9);
    }

    @DisplayName("예외처리 - 등록하지 않은 헤더정보")
    @ParameterizedTest
    @CsvSource({"Login-Info", "Session", "Version"})
    void handleException(String key) {
        assertThrows(NoSuchElementException.class, () -> {
            requestHeader.findByKey(key);
        });
    }
}