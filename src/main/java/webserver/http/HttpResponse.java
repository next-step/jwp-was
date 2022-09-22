package webserver.http;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class HttpResponse implements HttpMessage {
	private HttpStatus statusCode;
	private HttpVersion protocol;
	private MultiValueMap<String, String> headers;
	private byte[] content;
	private List<Cookie> cookies;
	public HttpResponse(OutputStream outputStream) {

	}

	public void sendRedirect(String location) {
		// setStatusCode(HttpStatus.FOUND);
		// setHeader("Location", location);
	}

	public void addCookie(Cookie cookie) {
		if(cookies == null) {
			cookies = new ArrayList<>();
		}
		cookies.add(cookie);
	}

	public void addHeader(String key, String value) {
		if(headers == null) {
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

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public MultiValueMap<String, String> getHeaders() {
		return null;
	}

	@Override
	public HttpVersion getProtocol() {
		return null;
	}

	public String toEncoded(){
		StringBuilder builder = new StringBuilder();
		builder.append("HTTP/1.1")
			.append(" ")
			.append(statusCode.getValue())
			.append(" ")
			.append(statusCode.getReasonPhrase())
			.append("\r\n");
		for(String key : headers.keySet()) {
			builder.append(key)
				.append(": ")
				.append(headers.getFirst(key))
				.append("\r\n");
		}

		if(cookies != null) {
			for(Cookie cookie : cookies) {
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
