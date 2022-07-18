package utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.WebApplicationServer;

public class IOUtils {

	private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

	/**
	 * @param BufferedReader는
	 *            Request Body를 시작하는 시점이어야
	 * @param contentLength는
	 *            Request Header의 Content-Length 값이다.
	 * @return
	 * @throws IOException
	 */
	public static String readData(BufferedReader br, int contentLength) throws IOException {
		char[] body = new char[contentLength];
		br.read(body, 0, contentLength);
		return String.copyValueOf(body);
	}

	public static List<String> readData(InputStream inputStream) {
		List<String> lines = new ArrayList<>();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			String line = br.readLine();
			while (line != null && !line.isEmpty()) {
				lines.add(line);
				line = br.readLine();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException("요청을 읽을 수 없습니다.", e);
		}

		return lines;
	}
}
