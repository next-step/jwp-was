package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {
	private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

	private final DataOutputStream dos;

	private final Map<String, String> headers = new HashMap<>();

	public HttpResponse(OutputStream out) {
		this.dos = new DataOutputStream(out);
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public void sendRedirect(String redirectUrl) {
		try {
			dos.writeBytes("HTTP/1.1 302 Found\r\n");
			writeHeaders();
			dos.writeBytes("Location: " + redirectUrl + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void responseBody(byte[] body) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			writeHeaders();
			dos.writeBytes("Content-Length: " + body.length + "\r\n");
			dos.writeBytes("\r\n");
			dos.write(body, 0, body.length);
			dos.writeBytes("\r\n");
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void writeHeaders() {
		Set<String> keys = headers.keySet();
		for (String key : keys) {
			try {
				dos.writeBytes(key + ": " + headers.get(key) + "\r\n");
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

}
