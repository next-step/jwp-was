package webserver.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class HttpResponse implements HttpMessage {
	private static final String SET_COOKIE = "Set-Cookie";
	public static final String LOCATION = "Location";
	private HttpStatus httpStatus;
	private HttpVersion httpVersion;
	private MultiValueMap<String, String> headers;
	private final List<Cookie> cookies = new ArrayList<>();
	private final DataOutputStream writer;

	public HttpResponse(OutputStream outputStream) {
		this.writer = new DataOutputStream(outputStream);
	}

	public DataOutputStream getWriter() {
		return writer;
	}

	public void sendRedirect(String location) throws IOException {
		setHttpStatus(HttpStatus.FOUND);
		addHeader(LOCATION, location);
		writer.writeBytes(toEncoded());
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	@Override
	public void addHeader(String key, String value) {
		if (headers == null) {
			headers = new LinkedMultiValueMap<>();
		}
		headers.add(key, value);
	}

	@Override
	public String getHeader(String key) {
		return headers.getFirst(key);
	}

	@Override
	public MultiValueMap<String, String> getHeaders() {
		return this.headers;
	}

	@Override
	public void setHttpVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
	}

	@Override
	public HttpVersion getHttpVersion() {
		return this.httpVersion;
	}

	public String toEncoded() {
		StringBuilder builder = new StringBuilder();
		builder.append("HTTP/1.1")
			.append(" ")
			.append(httpStatus.getValue())
			.append(" ")
			.append(httpStatus.getReasonPhrase())
			.append("\r\n");
		if (headers != null) {
			headers.forEach((key, values) -> values.forEach(value -> builder.append(key)
				.append(": ")
				.append(value)
				.append("\r\n")));
		}
		builder.append("\r\n");
		return builder.toString();
	}

	public void addCookie(Cookie cookie) {
		cookies.add(cookie);
		addHeader(SET_COOKIE, cookie.toEncoded());
	}
}
