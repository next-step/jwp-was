package webserver.http;

import enums.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.MimeTypeUtils;
import utils.StringUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

public class HttpResponse {

	private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

	private static final String RESPONSE_HTTP_VERSION = "HTTP/1.1";
	private final HttpRequest httpRequest;

	private final HttpHeaders httpHeaders;

	private final DataOutputStream outputStream;

	private HttpStatus httpStatus = HttpStatus.OK;

	private byte[] responseBody;

	private HttpResponse(HttpRequest httpRequest, OutputStream out){
		this.httpRequest = httpRequest;
		this.httpHeaders = new HttpHeaders();
		this.outputStream = new DataOutputStream(out);
	}

	public static HttpResponse of(HttpRequest httpRequest, OutputStream out) {

		return new HttpResponse(httpRequest, out);
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public void setHttpHeader(String name, String value) {
		this.httpHeaders.setHeader(name, value);
	}

	public HttpHeaders getHttpHeaders(){
		return this.httpHeaders;
	}

	public List<String> getHttpHeaderLines(){
		return this.httpHeaders.getHeaderLines();
	}

	public byte[] getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(byte[] responseBody) {
		setHttpHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(responseBody.length));
		this.responseBody = responseBody;
	}

	public void sendRedirect(String redirectUrl) {
		this.setHttpStatus(HttpStatus.FOUND);
		this.setHttpHeader(HttpHeaders.LOCATION, redirectUrl);
	}

	public void sendResource(URL resourceUrl){
		byte[] body = FileIoUtils.loadFileFromURL(resourceUrl);
		this.setResponseBody(body);

		String mimeType = MimeTypeUtils.guessContentTypeFromName(resourceUrl.getFile(), httpRequest.getHeader(HttpHeaders.ACCEPT));
		this.setHttpHeader(HttpHeaders.CONTENT_TYPE, mimeType);
	}

	public void send404(){
		this.setHttpStatus(HttpStatus.NOT_FOUND);
	}

	public void writeResponse() {

		ifHasNewSessionSetCookie();

		writeStatusLine(this.outputStream, this.getHttpStatus());
		writeHeaderLines(this.outputStream, this.getHttpHeaderLines());
		writeBody(this.outputStream, this.getResponseBody());
		writeFlush(this.outputStream);
	}

	private void ifHasNewSessionSetCookie(){
		if(!this.httpRequest.hasNewSession()) {
			return;
		}

		HttpSession httpSession = this.httpRequest.getSession(false);
		if(httpSession == null) {
			return;
		}
		this.setHttpHeader(HttpHeaders.SET_COOKIE, httpSession.getCookieValue().getCookieSetLine());
	}

	private void writeStatusLine(DataOutputStream dos, HttpStatus httpStatus) {
		try {
			StringBuilder sb = new StringBuilder()
					.append(RESPONSE_HTTP_VERSION)
					.append(StringUtils.getWhiteSpace())
					.append(String.valueOf(httpStatus.getValue()))
					.append(StringUtils.getWhiteSpace())
					.append(httpStatus.getReasonPhrase())
					.append(StringUtils.getNewLine());
			dos.writeBytes(sb.toString());			
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void writeHeaderLines(DataOutputStream dos, List<String> headerLines) {
		try {
			for (String headerLine : headerLines) {
				dos.writeBytes(headerLine + StringUtils.getNewLine());
			}
			dos.writeBytes(StringUtils.getNewLine());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void writeBody(DataOutputStream dos, byte[] body) {
		if (body == null) {
			return;
		}

		try {
			dos.write(body, 0, body.length);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void writeFlush(DataOutputStream dos) {
		try {
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

}
