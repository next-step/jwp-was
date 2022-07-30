package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import utils.IOUtils;

public class HttpRequestParser {
	private HttpRequestParser() {
	}

	public static HttpRequest parse(InputStream inputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		return parse(reader);
	}

	public static HttpRequest parse(BufferedReader reader) throws IOException {
		String line = reader.readLine();
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
		return HttpRequestHeaders.of(headers);
	}

	public static HttpRequestBody parseBody(BufferedReader reader, HttpRequestHeaders headers) throws IOException {
		String body = "";
		if (headers.hasContentLength()) {
			body = IOUtils.readData(reader, headers.getContentLength());
		}
		return HttpRequestBody.of(body);
	}
}
