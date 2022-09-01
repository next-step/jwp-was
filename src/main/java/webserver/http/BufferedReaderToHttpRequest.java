package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.*;

public class BufferedReaderToHttpRequest {
	private static final Logger logger = LoggerFactory.getLogger(BufferedReaderToHttpRequest.class);
	private final BufferedReader bufferedReader;

	public BufferedReaderToHttpRequest(InputStream in) {
		try {
			this.bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private BufferedReaderToHttpRequest(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	public HttpRequest parsed() {
		try {
			RequestLine requestLine = RequestLine.from(firstLine(bufferedReader));
			logger.debug("request line : {}", requestLine);

			HttpHeaders headers = HttpHeaders.create(bufferedReader);
			logger.debug("http headers : {}", headers);

			RequestParams params = new RequestParams(requestLine.query(), IOUtils.readData(bufferedReader, headers.getInt("Content-Length")));

			return new HttpRequest(requestLine, headers, params);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private String firstLine(BufferedReader br) throws IOException {
		String firstLine = br.readLine();
		if (firstLine == null) {
			throw new IllegalArgumentException();
		}
		return firstLine;
	}
}
