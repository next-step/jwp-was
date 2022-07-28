package webserver.http.response;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import webserver.http.ContentType;
import webserver.http.request.HttpProtocol;

public class HttpResponse {
	private final String TEMPLATES_ROOT_PATH = "./templates";
	private final String STATIC_ROOT_PATH = "./static";
	private final String NEW_LINE = "\r\n";

	private final OutputStream outputStream;
	private final HttpResponseHeaders headers;
	private final HttpStatusLine statusLine;
	private HttpResponseBody responseBody;

	public HttpResponse(HttpProtocol httpProtocol, OutputStream outputStream) {
		this.outputStream = outputStream;
		this.headers = new HttpResponseHeaders();
		this.statusLine = new HttpStatusLine(httpProtocol);
	}

	private String getRootPath(String path) {
		if (path.contains(ContentType.HTML.getExtension()) || path.contains(ContentType.ICO.getExtension())) {
			System.out.println("Path : " + TEMPLATES_ROOT_PATH + path);
			return TEMPLATES_ROOT_PATH + path;
		}
		System.out.println("Path : " + STATIC_ROOT_PATH + path);
		return STATIC_ROOT_PATH + path;
	}

	public void addHeader(String attribute, String value) {
		headers.addAttribute(attribute, value);
	}

	private void write(HttpStatus status) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(statusLine.getHttpStatusLine(status) + NEW_LINE);
		buffer.append(headers.getResponseHeaders());

		if (!Objects.isNull(responseBody)) {
			buffer.append(NEW_LINE + responseBody.getBody());
		}

		try {
			outputStream.write(buffer.toString().getBytes(StandardCharsets.UTF_8));
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void forward(String uri) {
		Resource resource = Resource.of(getRootPath(uri));
		headers.addContentType(resource.getContentType());
		headers.addContentLength(resource.getContentLength());
		responseBody = new HttpResponseBody(resource);
		write(HttpStatus.OK);
	}

	public void sendRedirect(String uri) {
		headers.addLocation(uri);
		write(HttpStatus.FOUND);
	}

	public void forwardBody(ContentType contentType, String body) {
		headers.addContentType(contentType);
		headers.addContentLength(body.getBytes(StandardCharsets.UTF_8).length);
		responseBody = new HttpResponseBody(body);
		write(HttpStatus.OK);
	}

	public void response200Header(int contentLength) {
	}

	public void responseBody(byte[] body) {
	}

	public void processHeaders() {
	}
}
