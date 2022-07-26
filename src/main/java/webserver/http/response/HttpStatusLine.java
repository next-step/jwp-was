package webserver.http.response;

import java.util.Objects;

import webserver.http.request.HttpProtocol;

public class HttpStatusLine {
	private static final String FORMAT_STATUS_LINE = "%s %s %s";

	private final HttpProtocol httpProtocol;
	private final HttpStatus httpStatus;

	public HttpStatusLine(HttpProtocol httpProtocol, HttpStatus httpStatus) {
		this.httpProtocol = httpProtocol;
		this.httpStatus = httpStatus;
	}

	public String getHttpStatusLine() {
		return String.format(FORMAT_STATUS_LINE, httpProtocol.getProtocol(), httpStatus.getCode(), httpStatus.getMessage());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		HttpStatusLine that = (HttpStatusLine) o;
		return Objects.equals(httpProtocol, that.httpProtocol) && httpStatus == that.httpStatus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(httpProtocol, httpStatus);
	}
}
