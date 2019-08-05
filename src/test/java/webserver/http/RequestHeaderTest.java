package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderTest {
    @ParameterizedTest
    @ValueSource(strings = {
        "Host: localhost:8080\n" +
        "Connection: keep-alive\n" +
        "Cache-Control: max-age=0\n" +
        "Upgrade-Insecure-Requests: 1\n" +
        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\n" +
        "Accept-Encoding: gzip, deflate, br\n" +
        "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
        "Cookie: Idea-efa8df2d=1bb5567d-7139-475d-a323-ee4e3492e089; Idea-dcb08711=75f15d7b-5df3-4803-abcc-3cc01d84def2"
    })
    void create_requestHeader(String reqHeader) throws IOException {
        //given
        BufferedReader br = new BufferedReader
                (new InputStreamReader(
                        new ByteArrayInputStream(reqHeader.getBytes())));

        //when
        RequestHeader requestHeader = RequestHeader.newInstance(br);

        //then
        assertThat(requestHeader.findByKey("Connection")).isEqualTo("keep-alive");

    }
}
