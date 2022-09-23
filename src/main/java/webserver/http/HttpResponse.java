package webserver.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class HttpResponse implements HttpMessage {
	private HttpStatus httpStatus;
	private HttpVersion protocol;
	private MultiValueMap<String, String> headers;
	private byte[] content;
	private List<Cookie> cookies;
	private DataOutputStream writer;

	public HttpResponse(OutputStream outputStream) {
		this.writer = new DataOutputStream(outputStream);
	}

	public DataOutputStream getWriter() {
		return writer;
	}

	public void sendRedirect(String location) throws IOException {
		sendRedirect(location, HttpStatus.FOUND);
	}

	public void addCookie(Cookie cookie) {
		if (cookies == null) {
			cookies = new ArrayList<>();
		}
		cookies.add(cookie);
	}

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

	public void setProtocol(HttpVersion protocol) {
		this.protocol = protocol;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	@Override
	public MultiValueMap<String, String> getHeaders() {
		return null;
	}

	@Override
	public HttpVersion getProtocol() {
		return null;
	}

	private void sendRedirect(String location, HttpStatus httpStatus) throws IOException {
		setHttpStatus(httpStatus);
		addHeader("Location", location);
		writer.writeBytes(toEncoded());
	}

	public String toEncoded() {
		StringBuilder builder = new StringBuilder();
		builder.append("HTTP/1.1")
			.append(" ")
			.append(httpStatus.getValue())
			.append(" ")
			.append(httpStatus.getReasonPhrase())
			.append("\r\n");
		for (String key : headers.keySet()) {
			builder.append(key)
				.append(": ")
				.append(headers.getFirst(key))
				.append("\r\n");
		}

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				builder.append("Set-Cookie: ")
					.append(cookie.getName())
					.append("=")
					.append(cookie.getValue())
					.append("\r\n");
			}
		}
		builder.append("\r\n");
		return builder.toString();
	}
}
