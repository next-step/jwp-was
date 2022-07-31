package webserver.http.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestParserTest {
	@Test
	@DisplayName("헤더 파싱")
	public void parseHeaders() throws IOException {
		String header = "Host: localhost\r\nConnection: keep-alive\r\nAccept: */*\r\nCookie: logined=true\r\n";
		StringReader sr = new StringReader(header);
		BufferedReader reader = new BufferedReader(sr);

		HttpRequestHeaders expect = HttpRequestHeaders.of(Map.of("Host", "localhost",
															 "Connection", "keep-alive",
															 "Accept", "*/*",
															 "Cookie", "logined=true"));
		HttpRequestHeaders actual = HttpRequestParser.parseHeaders(reader);

		assertThat(actual).isEqualTo(expect);
	}

	@Test
	@DisplayName("바디 파싱")
	public void parseBody() throws IOException {
		String body = "userId=javajigi&password=password&name=TEST&email=javajigi@slipp.net";
		HttpRequestHeaders header = HttpRequestHeaders.of(Map.of("Content-Length", String.valueOf(body.getBytes(StandardCharsets.UTF_8).length)));
		StringReader sr = new StringReader(body);
		BufferedReader reader = new BufferedReader(sr);

		HttpRequestBody expect = HttpRequestBody.of(body);
		HttpRequestBody actual = HttpRequestParser.parseBody(reader, header);

		assertThat(actual).isEqualTo(expect);
	}

	@Test
	@DisplayName("Request 파싱")
	public void parse() throws IOException {
		String data = "POST /user/create HTTP/1.1\r\nHost: localhost:8080\r\nConnection: keep-alive\r\nContent-Length: 68\r\nContent-Type: "
					  + "application/x-www-form-urlencoded\r\nAccept: */*\r\n \r\nuserId=javajigi&password=password&name=TEST&email=javajigi@slipp.net\r\n";
		StringReader sr = new StringReader(data);
		BufferedReader reader = new BufferedReader(sr);
		HttpRequest actual = HttpRequestParser.parse(reader);

		HttpRequestLine line = HttpRequestLine.of("POST /user/create HTTP/1.1");
		HttpRequestHeaders headers = HttpRequestHeaders.of(Map.of("Host", "localhost",
															 "Connection", "keep-alive",
															 "Content-Length", "68",
															 "Content-Type", "application/x-www-form-urlencoded",
															 "Accept", "*/*"));
		HttpRequestBody body = HttpRequestBody.of("userId=javajigi&password=password&name=TEST&email=javajigi@slipp.net");

		assertThat(actual).isEqualTo(new HttpRequest(line, headers, body));
	}
}
