package webserver.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import enums.HttpMethod;
import webserver.resolvers.body.FormBodyResolver;

public class HttpBodyRequestTest {


    private static final String HTTP_PLAIN_POST = "POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 93\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";


    private static final String HTTP_QUERY_STRING_POST = "POST /user/create?id=1 HTTP/1.1\n" + 
    		"Host: localhost:8080\n" + 
    		"Connection: keep-alive\n" + 
    		"Content-Length: 46\n" + 
    		"Content-Type: application/x-www-form-urlencoded\n" + 
    		"Accept: */*\n" + 
    		"\n" + 
    		"userId=javajigi&password=password&name=JaeSung";



    @DisplayName("Post Http HttpBodyRequest 테스트 : parameter {0}")
    @ParameterizedTest
    @CsvSource({"userId,javajigi", "password,password", "email,javajigi@slipp.net"})
    public void httpRequestParse(String parameterName, String expectedValue){
        try{
            FormBodyResolver formBodyResolver = FormBodyResolver.getInstance();
            HttpRequest httpBodyRequest = formBodyResolver.resolve(HttpBaseRequest.parse(new ByteArrayInputStream(HTTP_PLAIN_POST.getBytes())));
            assertThat(httpBodyRequest.getParameter(parameterName)).isEqualTo(expectedValue);
        } catch (Exception e) {
            assertThat(e).doesNotThrowAnyException();
            assertThat(e).hasNoCause();
        }
    }

    @DisplayName("Post Http HttpBodyRequest 테스트 : with QueryString parameter")
    @Test
    public void httpRequestParseQueryString() throws Exception {
    	FormBodyResolver formBodyResolver = FormBodyResolver.getInstance();
        HttpRequest httpRequest = formBodyResolver.resolve(HttpBaseRequest.parse(new ByteArrayInputStream(HTTP_QUERY_STRING_POST.getBytes())));
        assertEquals(HttpMethod.POST, httpRequest.getMethod());
        assertEquals("/user/create", httpRequest.getRequestURI());
        assertEquals("keep-alive", httpRequest.getHeader("Connection"));
        assertEquals("1", httpRequest.getParameter("id"));
        assertEquals("javajigi", httpRequest.getParameter("userId"));
    }



}
