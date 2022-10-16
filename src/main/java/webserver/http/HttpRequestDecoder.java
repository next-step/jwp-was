package webserver.http;

import java.net.URI;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.MultiValueMap;

import io.netty.buffer.ByteBuf;

public class HttpRequestDecoder extends HttpMessageDecoder<HttpRequest> {
	private static final int HTTP_METHOD_INDEX = 0;
	private static final int HTTP_URI_INDEX = 1;
	private static final int HTTP_VERSION_INDEX = 2;
	private static final int LINE_ELEMENT_SIZE = 3;

	@Override
	protected HttpRequest initialize(ByteBuf firstLine, MultiValueMap<String, String> headers, ByteBuf inboundBytes) {

		return new DefaultRequest(decodeFirstLine(firstLine), headers, getBytes(inboundBytes));
	}

	protected RequestLine decodeFirstLine(ByteBuf buffer) {
		List<String> requestLine = lineSplitter.split(buffer);
		if (hasBlank(requestLine)) {
			throw new IllegalArgumentException("Invalid separator. only a single space or horizontal tab allowed.");
		}

		if (requestLine.size() != LINE_ELEMENT_SIZE) {
			throw new IllegalArgumentException(
				"Invalid argument count exception. There must be 3 elements, but received (" + requestLine.size()
					+ ")");
		}

		HttpMethod method = HttpMethod.of(requestLine.get(HTTP_METHOD_INDEX));
		URI uri = URI.create(requestLine.get(HTTP_URI_INDEX));
		HttpVersion httpVersion = HttpVersion.of(findElement(requestLine, HTTP_VERSION_INDEX));
		return new RequestLine(method, uri, httpVersion);
	}
	private String findElement(List<String> requestLine, int index) {
		return requestLine.get(index);
	}

	private boolean hasBlank(List<String> requestLine) {
		return requestLine.stream()
			.anyMatch(Strings::isBlank);
	}
}
