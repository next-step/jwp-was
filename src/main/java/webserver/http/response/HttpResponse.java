package webserver.http.response;

import java.nio.charset.StandardCharsets;

import webserver.http.ContentType;
import webserver.http.request.HttpProtocol;

public class HttpResponse {
	private static final String NEW_LINE = "\r\n";

	private final HttpStatusLine httpStatusLine;
	private final HttpResponseHeaders httpResponseHeaders;
	private final HttpResponseBody httpResponseBody;

	public static class Builder {
		private HttpStatusLine httpStatusLine;
		private HttpResponseHeaders httpResponseHeaders = new HttpResponseHeaders();
		private HttpResponseBody httpResponseBody;

		public Builder statusLine(HttpProtocol httpProtocol, HttpStatus httpStatus) {
			httpStatusLine = new HttpStatusLine(httpProtocol, httpStatus);
			return this;
		}

		public Builder responseBody(Resource resource) {
			httpResponseBody = new HttpResponseBody(resource);
			return this;
		}

		public Builder emptyBody() {
			httpResponseBody = new HttpResponseBody("");
			return this;
		}

		public Builder contentType(ContentType contentType) {
			httpResponseHeaders.putContentType(contentType);
			return this;
		}

		public Builder contentLength(int contentLength) {
			httpResponseHeaders.putContentLength(contentLength);
			return this;
		}

		public Builder location(String location) {
			httpResponseHeaders.putLocation(location);
			return this;
		}
		public HttpResponse build() {
			return new HttpResponse(this);
		}
	}

	public byte[] getBytes() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(httpStatusLine.getHttpStatusLine());
		buffer.append(NEW_LINE);
		buffer.append(httpResponseHeaders.getResponseHeaders());
		buffer.append(NEW_LINE);
		buffer.append(httpResponseBody.getBody());
		return buffer.toString().getBytes(StandardCharsets.UTF_8);
	}

	private HttpResponse (Builder builder) {
		this.httpStatusLine = builder.httpStatusLine;
		this.httpResponseHeaders = builder.httpResponseHeaders;
		this.httpResponseBody = builder.httpResponseBody;
	}

	public HttpStatusLine getHttpStatusLine() {
		return httpStatusLine;
	}

	public HttpResponseHeaders getHttpResponseHeaders() {
		return httpResponseHeaders;
	}

	public HttpResponseBody getHttpResponseBody() {
		return httpResponseBody;
	}
}
