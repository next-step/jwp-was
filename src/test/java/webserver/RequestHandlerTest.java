package webserver;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.RequestLine;

import java.io.*;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {
    @ParameterizedTest
    @CsvSource({"GET / HTTP/1.1,GET,/", "POST /user/create HTTP/1.1,POST,/user/create"})
    void readHttpRequest(ArgumentsAccessor argumentsAccessor) throws IOException {
        //given
        InputStream inputStream = new ByteArrayInputStream(argumentsAccessor.getString(0).getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        //when
        RequestHandler requestHandler = new RequestHandler(null);
        RequestLine requestLine = requestHandler.readRequestLine(br);

        //then
        assertThat(requestLine.getMethod()).isEqualTo(argumentsAccessor.getString(1));
        assertThat(requestLine.getPath().getPath()).isEqualTo(argumentsAccessor.getString(2));
    }

//    @ParameterizedTest
//    @ValueSource(strings = {
//        "Host: localhost:8080\n" +
//        "Connection: keep-alive\n" +
//        "Cache-Control: max-age=0\n" +
//        "Upgrade-Insecure-Requests: 1\n" +
//        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36\n" +
//        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\n" +
//        "Accept-Encoding: gzip, deflate, br\n" +
//        "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
//        "Cookie: Idea-efa8df2d=1bb5567d-7139-475d-a323-ee4e3492e089; Idea-dcb08711=75f15d7b-5df3-4803-abcc-3cc01d84def2"
//    })
//    void readRequestHeader(String reqHeader) throws IOException {
//        //given
//        InputStream inputStream = new ByteArrayInputStream(reqHeader.getBytes());
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        RequestHandler requestHandler = new RequestHandler(null);
//
//        //when
//        Map<String, String> requestHeader = requestHandler.readRequestHeader(br);
//
//        //then
//        assertThat(requestHeader.get("Host")).isEqualTo("localhost:8080");
//    }
}