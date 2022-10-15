package webserver.servlet;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class DefaultServlet extends AbstractServlet {
	public void doService(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
		String path = request.getServletPath();
		byte[] content = loadResource(resolvePath(path));
		int contentLength = content.length;

		response.addHeader("Content-Type", resolveType(path));
		response.addHeader("Content-Length", String.valueOf(contentLength));

		response.getWriter().writeBytes(response.toEncoded());
		response.getWriter().write(content, 0, contentLength);
		response.getWriter().flush();
	}

	private byte[] loadResource(String resource) throws IOException, URISyntaxException {
		return FileIoUtils.loadFileFromClasspath(resource);
	}

	private String resolvePath(String path) {
		if (path.equals("/")) {
			path = "templates/index.html";
		} else if (path.startsWith("/js") || path.startsWith("/css") || path.startsWith("/fonts") || path.startsWith(
			"/images")) {
			path = "static" + path;
		} else {
			path = "templates" + path;
		}
		return path;
	}

	private String resolveType(String path) {
		String contentType = "text/html;charset=utf-8";
		if (path.endsWith(".css")) {
			contentType = "text/css;charset=utf-8";
		} else if (path.endsWith(".js")) {
			contentType = "application/javascript;charset=utf-8";
		} else if (path.endsWith(".png")) {
			contentType = "image/png";
		} else if (path.endsWith(".jpg")) {
			contentType = "image/jpeg";
		} else if (path.endsWith(".woff")) {
			contentType = "application/font-woff";
		} else if (path.endsWith(".woff2")) {
			contentType = "application/font-woff2";
		} else if (path.endsWith(".ttf")) {
			contentType = "application/x-font-ttf";
		} else if (path.endsWith(".svg")) {
			contentType = "image/svg+xml";
		} else if (path.endsWith(".ico")) {
			contentType = "image/x-icon";
		} else if (path.endsWith(".eot")) {
			contentType = "application/vnd.ms-fontobject";
		} else if (path.endsWith(".otf")) {
			contentType = "application/x-font-opentype";
		} else if (path.endsWith(".html")) {
			contentType = "text/html;charset=utf-8";
		}
		return contentType;
	}
}
