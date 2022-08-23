package webserver.http;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;

class HttpResponseTest {
	private String testDirectory = "./src/test/resources/";

	@Test
	public void response200Header() throws Exception {
		HttpResponse response = new HttpResponse(new FileOutputStream(testDirectory + "Http_200.txt"));
		response.response200Header(59);
	}

	@Test
	public void response200CssHeader() throws Exception {
		HttpResponse response = new HttpResponse(new FileOutputStream(testDirectory + "Http_200_css.txt"));
		response.response200CssHeader(59);
	}

	@Test
	public void response302LoginedHeader() throws Exception {
		HttpResponse response = new HttpResponse(new FileOutputStream(testDirectory + "Http_302_logined.txt"));
		response.response302LoginedHeader(true, "/index.html");
	}
}
