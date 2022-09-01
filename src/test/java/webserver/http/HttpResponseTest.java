package webserver.http;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;

class HttpResponseTest {
	private String testDirectory = "./src/test/resources/";

	@Test
	public void response() throws Exception {
		HttpResponse response = new HttpResponse(new FileOutputStream(testDirectory + "Http_200.txt"));
		response.addHeader("Content-Type", "text/html;charset=utf-8");
		response.responseBody("TEST".getBytes());
	}

	@Test
	public void response_css() throws Exception {
		HttpResponse response = new HttpResponse(new FileOutputStream(testDirectory + "Http_200_css.txt"));
		response.addHeader("Content-Type", "text/css;charset=utf-8");
		response.responseBody("TEST".getBytes());
	}

	@Test
	public void sendRedirectTest() throws Exception {
		HttpResponse response = new HttpResponse(new FileOutputStream(testDirectory + "Http_302_logined.txt"));
		response.addHeader("Set-Cookie", "logined=" + true);
		response.sendRedirect("/index.html");
	}
}
