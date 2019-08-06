package servlet;

import static org.assertj.core.api.Assertions.assertThat;

import http.HttpRequest;
import http.Parameters;
import http.RequestHeader;
import http.RequestLine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {

  @Test
  @DisplayName("GET 방식 요청 파싱")
  void getParsing() throws IOException {
    StringBuffer queryString = new StringBuffer();
    queryString.append("GET /users?userId=javajigi&password=password&name=JaeSung&noValue= HTTP/1.1\n");

    StringBuffer header = new StringBuffer();
    header.append("Host: localhost:8080\n");
    header.append("Connection: keep-alive\n");
    header.append("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)\n");
    header.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9\n");
    header.append("Referer: http://localhost:8080/user/form.html\n");
    header.append("Accept-Encoding: gzip, deflate, br\n");
    header.append("Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n");

    BufferedReader headerStream = new BufferedReader(new StringReader(header.toString()));

    RequestLine requestLine = RequestLine.parse(queryString.toString());
    RequestHeader requestHeader = RequestHeader.parse(headerStream);

    HttpRequest httpRequest = new HttpRequest(requestLine,requestHeader,null);

    assertThat(httpRequest.getParameters()).isEqualTo(Parameters.parse("userId=javajigi&password=password&name=JaeSung&noValue="));
    assertThat(httpRequest.getRequestLine()).isEqualTo(RequestLine.parse("GET /users?userId=javajigi&password=password&name=JaeSung&noValue= HTTP/1.1\n"));
    assertThat(httpRequest.getRequestHeader()).isEqualTo(requestHeader);
  }

  @Test
  @DisplayName("post 방식 요청 파싱")
  void postParsing() throws IOException {
    StringBuffer queryString = new StringBuffer();
    queryString.append("POST /user/create HTTP/1.1");

    StringBuffer header = new StringBuffer();
    header.append("Host: localhost:8080\n");
    header.append("Connection: keep-alive\n");
    header.append("Content-Length: 59\n");
    header.append("Content-Type: application/x-www-form-urlencoded\n");
    header.append("Accept: */*\n");
    header.append("\n");

    String requestBody = "userId=javajigi&password=password&name=jaesung&email=javajigi@slipp.net";

    BufferedReader headerStream = new BufferedReader(new StringReader(header.toString()));

    RequestLine requestLine = RequestLine.parse(queryString.toString());
    RequestHeader requestHeader = RequestHeader.parse(headerStream);
    HttpRequest httpRequest = new HttpRequest(requestLine, requestHeader, requestBody);

    assertThat(httpRequest.getParameters()).isEqualTo(Parameters
        .parse("userId=javajigi&password=password&name=jaesung&email=javajigi@slipp.net"));
    assertThat(httpRequest.getRequestLine())
        .isEqualTo(RequestLine.parse("POST /user/create HTTP/1.1"));
    assertThat(httpRequest.getRequestHeader()).isEqualTo(requestHeader);
  }

}
