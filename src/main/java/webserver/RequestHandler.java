package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.HttpRequest;
import webserver.http.HttpRequestDecoder;
import webserver.http.HttpResponse;
import webserver.servlet.Servlet;
import webserver.servlet.ServletMapping;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private Socket connection;
	private final HttpRequestDecoder httpRequestDecoder;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
		this.httpRequestDecoder = new HttpRequestDecoder();
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream inputStream = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

			HttpRequest httpRequest = httpRequestDecoder.decode(inputStream);
			HttpResponse httpResponse = new HttpResponse(out);

			String servletPath = httpRequest.getServletPath();

			Servlet servlet = ServletMapping.match(servletPath);
			servlet.service(httpRequest, httpResponse);

		} catch (IOException exception) {
			logger.error(exception.getMessage());
		}
	}
}
