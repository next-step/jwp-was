package webserver.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class HttpResponse implements HttpMessage {
	private HttpStatus httpStatus;
	private HttpVersion httpVersion;
	private MultiValueMap<String, String> headers;
	private byte[] content;
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
		for (String key : headers.keySet()) {
			builder.append(key)
				.append(": ")
				.append(headers.getFirst(key))
				.append("\r\n");
		}
		builder.append("\r\n");
		return builder.toString();
	}

	private void sendRedirect(String location, HttpStatus httpStatus) throws IOException {
		setHttpStatus(httpStatus);
		addHeader("Location", location);
		writer.writeBytes(toEncoded());
	}
}
