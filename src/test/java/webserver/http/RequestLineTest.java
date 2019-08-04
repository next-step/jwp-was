package webserver.http;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.google.gson.Gson;
import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.domain.HttpParseVO;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

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
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getParseResult().getMethod()).isEqualTo("GET");
        assertThat(requestLine.getParseResult().getUrlPath()).isEqualTo("/users");
    }

    @DisplayName("Step1-1 파라미터 파싱 및 null 값 체크")
    @ParameterizedTest
    @CsvSource({"GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1, password",
            "GET /users?userId=javajigi&password=&name=JaeSung HTTP/1.1, "
    })
    void parseParameter(String url, String value){
        RequestLine requestLine = RequestLine.parse(url);
        assertThat(requestLine.getParam("userId")).isEqualTo("javajigi");
        assertThat(requestLine.getParam("password")).isEqualTo(value);
        assertThat(requestLine.getParam("name")).isEqualTo("JaeSung");
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET /index.html HTTP/1.1\r\n" +
            "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "Accept: */*"})
    @DisplayName("Step2 헤더 파싱")
    void parseHeader(String httpFormStr){
        RequestLine requestLine = RequestLine.parse(httpFormStr);
        HttpParseVO httpParseVO = requestLine.getParseResult();
        assertThat(httpParseVO.getMethod()).isEqualTo("GET");
        assertThat(httpParseVO.getUrlPath()).isEqualTo("/index.html");
        assertThat(httpParseVO.getVersion()).isEqualTo("HTTP/1.1");
        assertThat(httpParseVO.getEtcHeader().get("Host")).isEqualTo("localhost:8080");
        assertThat(httpParseVO.getEtcHeader().get("Connection")).isEqualTo("keep-alive");
        assertThat(httpParseVO.getEtcHeader().get("Accept")).isEqualTo("*/*");
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*"})
    @DisplayName("Step2 헤더 파싱 및 파일 조회")
    void FileRead(String httpFormStr) throws URISyntaxException, IOException {
        RequestLine requestLine = RequestLine.parse(httpFormStr);
        HttpParseVO httpParseVO = requestLine.getParseResult();

        byte[] contentByte = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        String content = new String(contentByte);
        assertThat(httpParseVO.getReturnContent()).isEqualTo(content);
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
        RequestLine requestLine = RequestLine.parse(httpFormStr);
        HttpParseVO httpParseVO = requestLine.getParseResult();

        assertThat(httpParseVO.getUrlPath()).isEqualTo("/user/create");
        assertThat(httpParseVO.getResultCode()).isEqualTo("302");
        assertThat(requestLine.getParam("userId")).isEqualTo("javajigi");
        assertThat(requestLine.getParam("password")).isEqualTo("password");
    }

    @Test
    @DisplayName("HandleBars 라이브러리 테스트")
    void TestHandlebars() throws IOException {
        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compileInline("Hellow {{this}}!!");
        assertThat(template.apply("JAVASSSSSS")).isEqualTo("Hellow JAVASSSSSS!!");

        Template objectTemplate = handlebars.compileInline("OBJECT !!  {{userId}},{{name}},{{email}}");
        User ccc = new User("aaa", "bbb", "ccc", "ddd");
        System.out.println(objectTemplate.apply(ccc));

        DataBase.addUser(new User("aaa", "bbb", "ccc", "ddd"));
        DataBase.addUser(new User("111", "222", "333", "444"));
        List<User> userList = DataBase.findAll().stream().collect(Collectors.toList());
        Template listTemplate = handlebars.compileInline("TESTSSSS !!!! {{#each}}{{userId}},{{name}},{{email}}{{/each}}");


        System.out.println(listTemplate.apply(userList));
    }
}
