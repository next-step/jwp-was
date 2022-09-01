package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(StaticController.class);

	@Override
	void doGet(HttpRequest request, HttpResponse response) throws Exception {
		String path = request.getPath();

		if (FileIoUtils.needsTemplatesPath(path)) {
			processPageRequest(request, response);
		} else if (FileIoUtils.needsStaticPath(path)) {
			processStaticRequest(request, response);
		}
	}

	private void processPageRequest(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
		String path = "./templates" + request.getPath();
		byte[] body = FileIoUtils.loadFileFromClasspath(path);
		response.addHeader("Content-Type", "text/html;charset=utf-8");
		response.responseBody(body);
	}

	private void processStaticRequest(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
		String path = "./static" + request.getPath();
		byte[] body = FileIoUtils.loadFileFromClasspath(path);
		if (path.startsWith("./static/css")) {
			response.addHeader("Content-Type", "text/css;charset=utf-8");
		} else {
			response.addHeader("Content-Type", "text/html;charset=utf-8");
		}
		response.responseBody(body);
	}
}
