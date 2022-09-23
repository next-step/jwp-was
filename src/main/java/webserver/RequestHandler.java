package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import webserver.http.HttpRequest;
import webserver.http.HttpRequestDecoder;
import webserver.http.HttpResponse;
import webserver.servlet.Servlet;
import webserver.servlet.ServletMapping;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private static final int MAX_BUFFER_SIZE = 8192;

	private Socket connection;
	private HttpRequestDecoder httpRequestDecoder;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
		this.httpRequestDecoder = new HttpRequestDecoder();
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream inputStream = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

			ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
			buffer.writeBytes(inputStream, MAX_BUFFER_SIZE);

			HttpRequest httpRequest = httpRequestDecoder.decode(buffer);
			HttpResponse httpResponse = new HttpResponse(out);

			String path = httpRequest.getURI().getPath();
			logger.debug("Request path = " + path);

			Servlet servlet = ServletMapping.match(path);
			servlet.service(httpRequest, httpResponse);

		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (URISyntaxException e) {
			logger.error(e.getMessage());
		}
	}
}
