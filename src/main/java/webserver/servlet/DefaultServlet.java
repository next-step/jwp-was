package webserver.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import utils.FileIoUtils;
import webserver.http.HttpMessage;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class DefaultServlet extends AbstractServlet {

	public void doService(HttpRequest request, HttpResponse response) throws IOException {
		String requestUri = getRequestPath(request);

		ResourceType resourceType = ResourceType.of(requestUri);

		byte[] content = loadResource(getResourcePath(requestUri, resourceType));
		int contentLength = content.length;

		response.addHeader(HttpMessage.CONTENT_LENGTH, String.valueOf(contentLength));
		response.addHeader(HttpMessage.CONTENT_TYPE, resourceType.getContentType());

		response.getWriter().writeBytes(response.toEncoded());
		response.getWriter().write(content, 0, contentLength);
		response.getWriter().flush();
		response.getWriter().close();
	}

	private byte[] loadResource(String resource) throws FileNotFoundException {
		try {
			return FileIoUtils.loadFileFromClasspath(resource);
		} catch (Exception exception) {
			throw new FileNotFoundException("The specified path does not exist in the classpath. [" + resource + "]");
		}
	}
	private String getRequestPath(HttpRequest request) {
		String requestUri = request.getRequestUri();

		if (requestUri.equals("/")) {
			requestUri = "/index.html";
		}
		return requestUri;
	}

	private String getResourcePath(String requestUri, ResourceType resourceType) {
		return resourceType.getResourceRootPath() + requestUri;
	}

	enum ResourceType {
		HTML("text/html", ".html", ResourceType.TEMPLATES),
		JS("application/javascript;charset=utf-8", ".js", ResourceType.STATIC),
		CSS("text/css", ".css", ResourceType.STATIC),
		ICO("image/x-icon", ".ico", ResourceType.STATIC),
		PNG("image/png", ".png", ResourceType.STATIC),
		JPG("image/jpeg", ".jpg", ResourceType.STATIC),
		UNKNOWN("text/html", "", ResourceType.STATIC);

		private static final String TEMPLATES = "templates";
		public static final String STATIC = "static";
		private String contentType;
		private String extension;
		private String resourceRootPath;

		ResourceType(String contentType, String extension, String resourceRootPath) {
			this.contentType = contentType;
			this.extension = extension;
			this.resourceRootPath = resourceRootPath;
		}

		public String getContentType() {
			return contentType;
		}

		public String getExtension() {
			return extension;
		}

		public String getResourceRootPath() {
			return resourceRootPath;
		}

		public static ResourceType of(String requestUri) {
			return Arrays.stream(values())
				.filter(resourceType -> requestUri.endsWith(resourceType.getExtension()))
				.findFirst()
				.orElse(UNKNOWN);
		}
	}
}
