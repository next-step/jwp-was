package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import http.HttpMethod;
import http.Parameters;
import http.RequestHeader;
import http.RequestLine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.IOUtils;

class RequestHandlerTest {

  @Test
  @DisplayName("POST 요청시 Parameters를 생성을 테스트")
  void postRequestParametersTest() throws IOException {
    StringBuffer request = new StringBuffer();
    request.append("POST /user/create HTTP/1.1\n");
    request.append("Host: localhost:8080\n");
    request.append("Connection: keep-alive\n");
    request.append("Content-Length: 46\n");
    request.append("Content-Type: application/x-www-form-urlencoded\n");
    request.append("Accept: */*\n");
    request.append("\n");
    request.append("userId=javajigi&password=password&name=JaeSung");

    BufferedReader bufferedReader = new BufferedReader(new StringReader(request.toString()));

    String firstRequestLine = bufferedReader.readLine();
    RequestLine requestLine = RequestLine.parse(firstRequestLine);
    RequestHeader requestHeader = RequestHeader.parse(bufferedReader);

    assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
    assertThat(requestLine.getParameters()).isEqualTo(Parameters.EMPTY);
    assertThat(requestHeader.getAccept()).isEqualTo("*/*");

    String parameter = IOUtils.readData(bufferedReader, requestHeader.getContentLength());
    Parameters parameters = Parameters.parse(parameter);
    assertThat(parameters.getParameter("userId")).isEqualTo("javajigi");
    assertThat(parameters.getParameter("name")).isEqualTo("JaeSung");

  }
}