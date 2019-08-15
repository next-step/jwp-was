package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import utils.FileIoUtils;

import java.io.*;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    /**
     * 테스트를 먼저 만든뒤 코딩을 시작한다
     * 컴파일 에러난 상태에서 테스트 코드를 만든다
     *
     */
    @Test
    @DisplayName("Step0 기본 헤더 파싱")
    void parse(){
        HttpResponse response = new ServletContainer().getResponse("GET /users HTTP/1.1");
        assertThat(response.getHttpHeader().getMethod()).isEqualTo("GET");
        assertThat(response.getHttpHeader().getUrlPath()).isEqualTo("/users");
    }

    @DisplayName("Step1-1 파라미터 파싱 및 null 값 체크")
    @ParameterizedTest
    @CsvSource({"GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1, password",
            "GET /users?userId=javajigi&password=&name=JaeSung HTTP/1.1, "
    })
    void parseParameter(String url, String value){
        /*HttpResponse httpResponse = RequestLine.parse(httpController, url);
        assertThat(httpResponse.get().get("userId")).isEqualTo("javajigi");
        assertThat(httpResponse.getParameter().get("password")).isEqualTo(value);
        assertThat(httpResponse.getParameter().get("name")).isEqualTo("JaeSung");*/
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET /index.html HTTP/1.1\r\n" +
            "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "Accept: */*"})
    @DisplayName("Step2 헤더 파싱")
    void parseHeader(String httpFormStr){
        HttpResponse response = new ServletContainer().getResponse(httpFormStr);
        assertThat(response.getHttpHeader().getMethod()).isEqualTo("GET");
        assertThat(response.getHttpHeader().getUrlPath()).isEqualTo("/index.html");
        assertThat(response.getHttpHeader().getVersion()).isEqualTo("HTTP/1.1");
        assertThat(response.getHttpHeader().getEtcHeader().get("Host")).isEqualTo("localhost:8080");
        assertThat(response.getHttpHeader().getEtcHeader().get("Connection")).isEqualTo("keep-alive");
        assertThat(response.getHttpHeader().getEtcHeader().get("Accept")).isEqualTo("*/*");
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*"})
    @DisplayName("Step2 헤더 파싱 및 파일 조회")
    void FileRead(String httpFormStr) throws URISyntaxException, IOException {
        HttpResponse response = new ServletContainer().getResponse(httpFormStr);

        byte[] contentByte = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        String content = new String(contentByte);
        assertThat(response.getResultBody()).isEqualTo(content);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\r\n" +
            "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "Accept: */*",

            "POST /user/create HTTP/1.1\r\n" +
            "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "Content-Length: 59\r\n" +
            "Content-Type: application/x-www-form-urlencoded\r\n" +
            "Accept: */*\r\n" +
            "\r\n" +
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
    })
    @DisplayName("유저 회원 가입")
    void UserCreate(String httpFormStr){
        HttpResponse response = new ServletContainer().getResponse(httpFormStr);

        assertThat(response.getHttpHeader().getUrlPath()).isEqualTo("/user/create");
        assertThat(response.getStatusCode()).isEqualTo("302");
        //assertThat(.getParameter().get("userId")).isEqualTo("javajigi");
        //assertThat(httpRequest.getParameter().get("password")).isEqualTo("password");
    }


    @ParameterizedTest
    @ValueSource(strings = {
        "GET /index.html HTTP/1.1\r\n"+
        "Host: localhost:8080\r\n"+
        "Connection: keep-alive\r\n"+
        "Cache-Control: max-age=0\r\n"+
        "Upgrade-Insecure-Requests: 1\r\n"+
        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36\r\n"+
        "Sec-Fetch-Mode: navigate\r\n"+
        "Sec-Fetch-User: ?1\r\n"+
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\r\n"+
        "Sec-Fetch-Site: same-origin\r\n"+
        "Referer: http://localhost:8080/user/login.html\r\n"+
        "Accept-Encoding: gzip, deflate, br\r\n"+
        "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\r\n"+
        "Cookie: logined=true; token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXB0TmFtZSI6IuqwnOuwnOyLpCIsImRlcHRTZXEiOjQsIm1lbWJlclNlcSI6NjAsIm1lbWJlck5hbWUiOiLrsJXquLDtg5wiLCJleHAiOjE1NjU3NzU4MjcsIm1lbWJlcklkIjoiZWpraW0ifQ.I7NBRw9rH6HPLbkzIW3aElz1ECFf6NoptYK_wZc4D-I; JSESSIONID=382613A39102B3D8E675A9D6CED9E677\r\n"
    })
    @DisplayName("유저 회원 가입")
    void Cookies(String httpFormStr){
        HttpResponse response = new ServletContainer().getResponse(httpFormStr);

    }
}
