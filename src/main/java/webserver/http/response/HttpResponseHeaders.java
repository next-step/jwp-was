package webserver.http.response;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import webserver.http.ContentType;
import webserver.http.HttpCookie;
import webserver.http.HttpCookies;

public class HttpResponseHeaders {
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String CONTENT_LENGTH = "Content-Length";
	public static final String LOCATION = "Location";
	private static final String HEADER_DELIMITER = ": ";
	private static final String NEW_LINE = "\r\n";

	private final Map<String, String> headers = new HashMap<>();
	private final HttpCookies cookies = new HttpCookies();

	public void addContentType(ContentType contentType) {
		headers.put(CONTENT_TYPE, contentType.getMime());
	}

	public void addContentLength(int contentLength) {
		headers.put(CONTENT_LENGTH, String.valueOf(contentLength));
	}

	public void addLocation(String location) {
		headers.put(LOCATION, location);
	}

	public String getResponseHeaders() {
		StringBuilder stringBuilder = new StringBuilder();

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			stringBuilder.append(entry.getKey())
						 .append(HEADER_DELIMITER)
						 .append(entry.getValue())
						 .append(NEW_LINE);
		}

		return stringBuilder.toString();
	}

	public void addAttribute(String attribute, String value) {
		headers.put(attribute, value);
	}

	public void write(OutputStream outputStream) throws IOException {
		outputStream.write(getResponseHeaders().getBytes(StandardCharsets.UTF_8));
		cookies.write(outputStream);
	}

	public void addCookie(HttpCookie httpCookie) {
		cookies.addCookie(httpCookie);
	}
}
