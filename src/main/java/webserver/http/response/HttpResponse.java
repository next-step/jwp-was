package webserver.http.response;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import webserver.http.ContentType;
import webserver.http.request.HttpProtocol;

public class HttpResponse {
	private final String TEMPLATES_ROOT_PATH = "./templates";
	private final String STATIC_ROOT_PATH = "./static";

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
			return TEMPLATES_ROOT_PATH + path;
		}
		return STATIC_ROOT_PATH + path;
	}

	public void addHeader(String attribute, String value) {
		headers.addAttribute(attribute, value);
	}

	private void write(HttpStatus status) {
		try {
			statusLine.write(status, outputStream);
			headers.write(outputStream);
			responseBody.write(outputStream);
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void forward(String uri) {
		Resource resource = Resource.of(getRootPath(uri));
		response200Header(resource.getContentType(), resource.getContentLength());
		responseBody = new HttpResponseBody(resource);
		write(HttpStatus.OK);
	}

	public void sendRedirect(String uri) {
		headers.addLocation(uri);
		write(HttpStatus.FOUND);
	}

	public void forwardBody(ContentType contentType, String body) {
		response200Header(contentType, body.getBytes(StandardCharsets.UTF_8).length);
		responseBody = new HttpResponseBody(body);
		write(HttpStatus.OK);
	}

	public void response200Header(ContentType contentType, int contentLength) {
		headers.addContentType(contentType);
		headers.addContentLength(contentLength);
	}
}
