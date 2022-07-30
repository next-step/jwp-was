package webserver.http.response;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import webserver.http.ContentType;
import webserver.http.request.HttpProtocol;

public class HttpResponse {
	private final String TEMPLATES_ROOT_PATH = "./templates";
	private final String STATIC_ROOT_PATH = "./static";
	private final String NEW_LINE = "\r\n";

	private final OutputStream outputStream;
	private final HttpResponseHeaders headers;
	private final HttpStatusLine statusLine;

	public HttpResponse(HttpProtocol httpProtocol, OutputStream outputStream) {
		this.outputStream = outputStream;
		this.headers = new HttpResponseHeaders();
		this.statusLine = new HttpStatusLine(httpProtocol);
	}

	private String getRootPath(String path) {
		if (path.contains(ContentType.HTML.getExtension()) || path.contains(ContentType.ICO.getExtension())) {
			return TEMPLATES_ROOT_PATH + path;
		}
		return STATIC_ROOT_PATH + path;
	}

	public void addHeader(String attribute, String value) {
		headers.addAttribute(attribute, value);
	}

	public void forward(String uri) {
		Resource resource = Resource.of(getRootPath(uri));
		response200Header(resource.getContentType(), resource.getContentLength());
		write(HttpStatus.OK, resource.getResource());
	}

	public void sendRedirect(String uri) {
		headers.addLocation(uri);
		write(HttpStatus.FOUND);
	}

	public void forwardBody(ContentType contentType, String body) {
		byte[] responseBody = body.getBytes(StandardCharsets.UTF_8);
		response200Header(contentType, responseBody.length);
		write(HttpStatus.OK, responseBody);
	}

	public void response200Header(ContentType contentType, int contentLength) {
		headers.addContentType(contentType);
		headers.addContentLength(contentLength);
	}

	private void write(HttpStatus status, byte[] responseBody) {
		try {
			writeHeader(status);
			writeBody(responseBody);
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void write(HttpStatus status) {
		try {
			writeHeader(status);
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeHeader(HttpStatus status) throws IOException {
		statusLine.write(status, outputStream);
		headers.write(outputStream);
	}

	private void writeBody(byte[] responseBody) throws IOException {
		outputStream.write(NEW_LINE.getBytes(StandardCharsets.UTF_8));
		outputStream.write(responseBody);
	}
}
