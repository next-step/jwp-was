package webserver.servlet;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;

public class DefaultServlet implements Servlet {
	public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
		String path = request.getURI().getPath();
		String contentType = "text/css;charset=utf-8";
		if (path.equals("/")) {
			path = "/index.html";
		}

		if (path.endsWith(".js")) {
			contentType = "application/javascript;charset=utf-8";
		} else if (path.endsWith(".html")) {
			contentType = "text/html;charset=utf-8";
		}

		byte[] body = loadResource(path);
		response.setHttpVersion(HttpVersion.HTTP_1_1);
		response.setHttpStatus(HttpStatus.OK);
		response.addHeader("Content-Type", contentType);
		response.addHeader("Content-Length", String.valueOf(body.length));

		String message = response.toEncoded();
		response.getWriter().writeBytes(message);
		response.getWriter().write(body, 0, body.length);
		response.getWriter().flush();
	}
	private byte[] loadResource(String resource) throws IOException, URISyntaxException {
		if (resource.endsWith(".html")) {
			return FileIoUtils.loadFileFromClasspath("templates" + resource);

		}
		return FileIoUtils.loadFileFromClasspath("static" + resource);
	}
}
