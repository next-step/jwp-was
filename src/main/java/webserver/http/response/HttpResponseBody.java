package webserver.http.response;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpResponseBody {
	private final String NEW_LINE = "\r\n";
	private final String body;

	public HttpResponseBody(String body) {
		this.body = body;
	}

	public HttpResponseBody(Resource resource) {
		this(new String(resource.getResource()));
	}

	public String getBody() {
		return body;
	}

	public void write(OutputStream outputStream) throws IOException {
		if (!body.isEmpty()) {
			outputStream.write(NEW_LINE.getBytes(StandardCharsets.UTF_8));
			outputStream.write(body.getBytes(StandardCharsets.UTF_8));
		}
	}
}
