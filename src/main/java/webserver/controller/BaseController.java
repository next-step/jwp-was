package webserver.controller;

import webserver.http.ContentType;

public class BaseController extends AbstractController {
	protected final String STATIC_ROOT_PATH = "./static";

	protected String getRootPath(String path) {
		if (path.contains(ContentType.HTML.getExtension()) || path.contains(ContentType.ICO.getExtension())) {
			return TEMPLATES_ROOT_PATH + path;
		}
		return STATIC_ROOT_PATH + path;
	}
}
