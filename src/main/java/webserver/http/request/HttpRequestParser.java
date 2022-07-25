package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.IOUtils;

public class HttpRequestParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestParser.class);

	private HttpRequestParser() {
	}

	public static HttpRequest parse(InputStream inputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		return parse(reader);
	}

	public static HttpRequest parse(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		LOGGER.debug(line);
		HttpRequestLine requestLine = HttpRequestLine.of(line);
		HttpRequestHeaders headers = parseHeaders(reader);
		HttpRequestBody requestBody = parseBody(reader, headers);
		return new HttpRequest(requestLine, headers, requestBody);
	}

	public static HttpRequestHeaders parseHeaders(BufferedReader reader) throws IOException {
		Map<String, String> headers = new HashMap<>();
		String line = reader.readLine();
		while (line !=null && !line.isBlank()) {
			String[] parsedLine = line.split(HttpRequestHeaders.HEADER_DELIMITER);
			if (parsedLine.length > 1) {
				headers.put(parsedLine[HttpRequestHeaders.INDEX_KEY].trim(), parsedLine[HttpRequestHeaders.INDEX_VALUE].trim());
			}
			line = reader.readLine();
		}
		LOGGER.debug(headers.toString());
		return HttpRequestHeaders.of(headers);
	}

	public static HttpRequestBody parseBody(BufferedReader reader, HttpRequestHeaders headers) throws IOException {
		String body = "";
		if (headers.hasContentLength()) {
			body = IOUtils.readData(reader, headers.getContentLength());
		}
		LOGGER.debug(body);
		return HttpRequestBody.of(body);
	}
}
