package webserver.http.response;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import webserver.http.request.HttpProtocol;

public class HttpResponseTest {
	private String testDirectory = "./src/test/resources/http/";

	private OutputStream createOutputStream(String filename) throws FileNotFoundException {
		return new FileOutputStream(testDirectory + filename);
	}

	@Test
	public void responseForward() throws Exception {
		// Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
		HttpResponse response = new HttpResponse(HttpProtocol.of("HTTP/1.1"), createOutputStream("Http_Forward.txt"));
		response.forward("/index.html");
	}

	@Test
	public void responseRedirect() throws Exception {
		// Http_Redirect.txt 결과는 응답 headere에 Location 정보가 /index.html로 포함되어 있어야 한다.
		HttpResponse response = new HttpResponse(HttpProtocol.of("HTTP/1.1"), createOutputStream("Http_Redirect.txt"));
		response.sendRedirect("/index.html");
	}

	@Test
	public void responseCookies() throws Exception {
		// Http_Cookie.txt 결과는 응답 header에 Set-Cookie 값으로 logined=true 값이 포함되어 있어야 한다.
		HttpResponse response = new HttpResponse(HttpProtocol.of("HTTP/1.1"), createOutputStream("Http_Cookie.txt"));
		response.addHeader("Set-Cookie", "logined=true");
		response.sendRedirect("/index.html");
	}
}
