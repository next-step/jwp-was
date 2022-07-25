package webserver.http;

import java.util.Arrays;

public enum ContentType {
	JS(".js", "text/javascript"),
	CSS(".css", "text/css"),
	SVG(".svg", "image/svg+xml"),
	WOFF(".woff", "application/x-font-woff"),
	TFF(".ttf", "application/octet-stream"),
	ICO(".ico", "image/x-icon"),
	HTML(".html", "text/html;charset=utf-8");

	private static final String NOT_SUPPORTED_CONTENT_TYPE = "지원하지 않는 Content Type 입니다.";

	private final String extension;
	private final String mime;

	ContentType(String extension, String mime) {
		this.extension = extension;
		this.mime = mime;
	}

	public static ContentType findContentType(String uri) {
		return Arrays.stream(ContentType.values())
					 .filter(contentType -> uri.endsWith(contentType.extension))
					 .findAny()
					 .orElseThrow(() -> new IllegalArgumentException(NOT_SUPPORTED_CONTENT_TYPE));
	}

	public String getMime() {
		return mime;
	}

	public String getExtension() {
		return extension;
	}

}
