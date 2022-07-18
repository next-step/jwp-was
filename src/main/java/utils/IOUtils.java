package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class IOUtils {
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
		var bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		return bufferedReader.lines()
			.collect(Collectors.toList());
	}
}
