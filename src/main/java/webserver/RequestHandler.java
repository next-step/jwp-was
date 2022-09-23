package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import model.User;
import utils.FileIoUtils;
import webserver.http.Cookie;
import webserver.http.HttpRequest;
import webserver.http.HttpRequestDecoder;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;

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
			String path = httpRequest.getURI().getPath();
			logger.debug("Request path = " + path);
			if (path.equals("/user/create")) {
				String userId = httpRequest.getParameter("userId");
				String password = httpRequest.getParameter("password");
				String name = httpRequest.getParameter("name");
				String email = httpRequest.getParameter("email");

				User user = new User(userId, password, name, email);
				DataBase.addUser(user);
				HttpResponse httpResponse = new HttpResponse(out);
				httpResponse.setProtocol(HttpVersion.HTTP_1_1);
				httpResponse.sendRedirect("/");
			} else if (path.equals("/user/list.html")) { // 회원목록 조회
				String cookie = httpRequest.getHeader("Cookie");
				if (cookie != null && cookie.contains("logined=true")) {
					StringBuffer sb = new StringBuffer();
					sb.append("<table border=\"1\">");
					for (User user : DataBase.findAll()) {
						sb.append("<tr>");
						sb.append("<td>" + user.getUserId() + "</td>");
						sb.append("<td>" + user.getName() + "</td>");
						sb.append("<td>" + user.getEmail() + "</td>");
						sb.append("</tr>");
					}
					sb.append("</table>");
					byte[] body = sb.toString().getBytes();

					HttpResponse httpResponse = new HttpResponse(out);
					httpResponse.setProtocol(HttpVersion.HTTP_1_1);
					httpResponse.setHttpStatus(HttpStatus.OK);
					httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
					httpResponse.addHeader("Content-Length", String.valueOf(body.length));

					String message = httpResponse.toEncoded();
					httpResponse.getWriter().writeBytes(message);
					httpResponse.getWriter().write(body, 0, body.length);
					httpResponse.getWriter().flush();
				} else {
					HttpResponse httpResponse = new HttpResponse(out);
					httpResponse.setProtocol(HttpVersion.HTTP_1_1);
					httpResponse.sendRedirect("/user/login.html");
				}

			} else if (path.equals("/user/login")) { //(서비스) 로그인 버튼
				String userId = httpRequest.getParameter("userId");
				String password = httpRequest.getParameter("password");

				User user = DataBase.findUserById(userId);

				if (user == null || !user.getPassword().equals(password)) {
					HttpResponse httpResponse = new HttpResponse(out);
					httpResponse.setProtocol(HttpVersion.HTTP_1_1);
					httpResponse.addCookie(new Cookie("logined", "false"));
					httpResponse.sendRedirect("/user/login_failed.html");
				} else {
					HttpResponse httpResponse = new HttpResponse(out);
					httpResponse.setProtocol(HttpVersion.HTTP_1_1);
					httpResponse.addCookie(new Cookie("logined", "true"));
					httpResponse.sendRedirect("/");
				}
			} else if (path.equals("/user/login_failed.html")) {
				byte[] body = loadResource("/user/login_failed.html");

				HttpResponse httpResponse = new HttpResponse(out);
				httpResponse.setProtocol(HttpVersion.HTTP_1_1);
				httpResponse.setHttpStatus(HttpStatus.OK);
				httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
				httpResponse.addHeader("Content-Length", String.valueOf(body.length));

				String message = httpResponse.toEncoded();
				httpResponse.getWriter().writeBytes(message);
				httpResponse.getWriter().write(body, 0, body.length);
				httpResponse.getWriter().flush();
			} else {
				String contentType = "text/css;charset=utf-8";

				if (path.equals("/")) {
					path = "/index.html";
				} else if (path.endsWith(".html")) {
					contentType = "text/html;charset=utf-8";
				}
				byte[] body = loadResource(path);
				HttpResponse httpResponse = new HttpResponse(out);
				httpResponse.setProtocol(HttpVersion.HTTP_1_1);
				httpResponse.setHttpStatus(HttpStatus.OK);
				httpResponse.addHeader("Content-Type", contentType);
				httpResponse.addHeader("Content-Length", String.valueOf(body.length));

				String message = httpResponse.toEncoded();
				httpResponse.getWriter().writeBytes(message);
				httpResponse.getWriter().write(body, 0, body.length);
				httpResponse.getWriter().flush();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private byte[] loadResource(String resource) throws IOException, URISyntaxException {
		if (resource.endsWith(".html")) {
			return FileIoUtils.loadFileFromClasspath("templates" + resource);

		}
		return FileIoUtils.loadFileFromClasspath("static" + resource);
	}

	private void response302Header(DataOutputStream dos, String location) {
		try {
			dos.writeBytes("HTTP/1.1 302 Found \r\n");
			dos.writeBytes("Location: " + location + " \r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
		}
	}

	private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
