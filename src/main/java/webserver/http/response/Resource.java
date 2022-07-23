package webserver.http.response;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import webserver.http.ContentType;

public class Resource {
	private static final String NOT_FOUND_RESOURCE = "파일을 찾을 수 없습니다.";
	private static final String FAIL_INTERPRET_SYNTAX = "구문을 분석할 수 없습니다.";
	private static final String FAIL_IO_ERROR = "I/O Error";

	private final ContentType contentType;
	private final byte[] resource;

	private Resource(ContentType contentType, byte[] resource) {
		this.contentType = contentType;
		this.resource = resource;
	}

	public static Resource of(String path){
		byte[] bytes;
		try {
			bytes = FileIoUtils.loadFileFromClasspath(path);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(FAIL_INTERPRET_SYNTAX);
		} catch (NullPointerException e) {
			throw new NullPointerException(NOT_FOUND_RESOURCE);
		} catch (IOException e) {
			throw new IllegalArgumentException(FAIL_IO_ERROR);
		}

		return new Resource(ContentType.findContentType(path), bytes);
	}

	public ContentType getContentType() {
		return contentType;
	}

	public int getContentLength() {
		return resource.length;
	}

	public byte[] getResource() {
		return resource;
	}
}
