package webserver.http.view.request;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.domain.Cookie;
import webserver.http.domain.Cookies;
import webserver.http.view.CookiesParser;
import webserver.http.domain.Headers;
import webserver.http.view.HeadersParser;
import webserver.http.domain.Protocol;
import webserver.http.domain.request.Method;
import webserver.http.domain.request.Parameters;
import webserver.http.domain.request.Request;
import webserver.http.domain.request.RequestLine;
import webserver.http.domain.request.URI;
import webserver.http.view.KeyValuePairParser;
import webserver.http.view.ProtocolParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestReaderTest {
    private RequestReader requestReader;

    @BeforeEach
    void setUp() {
        KeyValuePairParser keyValuePairParser = new KeyValuePairParser();
        ParametersParser parametersParser = new ParametersParser(keyValuePairParser);
        URIParser uriParser = new URIParser(keyValuePairParser, parametersParser);

        requestReader = new RequestReader(
                new RequestLineParser(uriParser, new ProtocolParser()),
                new HeadersParser(keyValuePairParser),
                parametersParser,
                new CookiesParser(keyValuePairParser)
        );
    }

    @DisplayName("Content-Length 헤더가 없거나, Content-Type이 application/x-www-form-urlencoded이 아닌 경우, request line과 header 만 포함된 request 객체 생성")
    @Test
    void read_empty_body() throws IOException {
        String message = "GET /path?name=jordy&age=20 HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept: application/json\r\n" +
                "\r\n";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(message));

        Request request = requestReader.read(bufferedReader);

        assertThat(request).usingRecursiveComparison()
                .isEqualTo(getEmptyBodyRequest());
    }

    private Request getEmptyBodyRequest() {
        return new Request(
                new RequestLine(
                        Method.GET,
                        new URI("/path",
                            new Parameters(
                                    Map.of(
                                    "name", Lists.list("jordy"),
                                    "age", Lists.list("20")
                                    )
                            )
                        ),
                        new Protocol("HTTP", "1.1")
                ),
                new Headers(
                        Map.of(
                        "Host", "localhost:8080",
                        "Accept", "application/json"
                        )
                )
        );
    }

    @DisplayName("Cookie 헤더를 포함한 경우, request line과 header와 Cookies를 포함하는 request 객체 생성")
    @Test
    void read_with_cookie() throws IOException {
        String message = "GET /path?name=jordy&age=20 HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept: application/json\r\n" +
                "Cookie: logined=true; item=; type=intellij\r\n" +
                "\r\n";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(message));

        Request request = requestReader.read(bufferedReader);

        assertThat(request).usingRecursiveComparison()
                .isEqualTo(getRequestIncludingCookies());
    }

    private Request getRequestIncludingCookies() {
        return new Request(
                new RequestLine(
                        Method.GET,
                        new URI("/path",
                                new Parameters(
                                        Map.of(
                                                "name", Lists.list("jordy"),
                                                "age", Lists.list("20")
                                        )
                                )
                        ),
                        new Protocol("HTTP", "1.1")
                ),
                new Headers(
                        Map.of(
                                "Host", "localhost:8080",
                                "Accept", "application/json",
                                "Cookie", "logined=true; item=; type=intellij"
                        )
                ),
                new Cookies(
                        Map.of(
                                "logined", new Cookie("logined", "true"),
                                "item", new Cookie("item", ""),
                                "type", new Cookie("type", "intellij")
                        )
                )
        );
    }

    @DisplayName("양수 Content-Length 헤더와 application/x-www-form-urlencoded 타입의 Content-Type을 갖는 경우, request line과 header, request body까지 포함된 request 객체 생성")
    @Test
    void read_full_request_with_body() throws IOException {
        String message = "POST /path HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept: application/json\r\n" +
                "Content-Length: 17\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" +
                "name=jordy&age=20";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(message));

        Request request = requestReader.read(bufferedReader);

        assertThat(request).usingRecursiveComparison()
                .isEqualTo(getRequestWithBody());
    }

    private Request getRequestWithBody() {
        return new Request(
                new RequestLine(
                        Method.POST,
                        new URI("/path",
                                new Parameters(
                                        Map.of(
                                                "name", Lists.list("jordy"),
                                                "age", Lists.list("20")
                                        )
                                )
                        ),
                        new Protocol("HTTP", "1.1")
                ),
                new Headers(
                        Map.of(
                                "Host", "localhost:8080",
                                "Accept", "application/json",
                                "Content-Length", "17",
                                "Content-Type", "application/x-www-form-urlencoded"
                        )
                )
        );
    }
}