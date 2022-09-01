package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpHeaders {
	static final String COOKIE = "Cookie";
    static final String HEADER_DELIMITER = ":";

    private final Map<String, String> headers = new HashMap<>();

    public void add(String header) {
        String[] tokens = header.split(HEADER_DELIMITER);
        headers.put(tokens[0], tokens[1].trim());
    }

	public static HttpHeaders create(BufferedReader br) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		String line;
		while (!(line = br.readLine()).equals("")) {
			headers.add(line);
		}
		return headers;
	}

    public String get(String name) {
        return headers.get(name);
    }

    public int getInt(String name) {
        String header = get(name);
        if (header == null) {
            return 0;
        }
        return Integer.parseInt(header);
    }

	public HttpCookie getCookie() {
		return new HttpCookie(get(COOKIE));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		HttpHeaders that = (HttpHeaders) o;
		return Objects.equals(headers, that.headers);
	}

	@Override
	public int hashCode() {
		return Objects.hash(headers);
	}

	@Override
	public String toString() {
		return "HttpHeaders{" +
			"headers=" + headers +
			'}';
	}
}
