package webserver;

import java.io.*;
import java.net.Socket;

import db.DataBase;
import http.HttpMethod;
import http.Path;
import http.RequestHeaderLine;
import http.RequestHeaderLineType;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader((new InputStreamReader(in, "UTF-8")));
            String line = br.readLine();
            Path path = null;
            RequestHeaderLine requestHeaderLine = null;
            int contentLength = 0;
            boolean isLoginSuccess = false;
            while(line != null) {
                logger.debug("line : {}", line);
                RequestHeaderLineType requestHeaderLineType = RequestHeaderLineType.parse(line);
                if (requestHeaderLineType == RequestHeaderLineType.FIRST_LINE) {
                    requestHeaderLine = RequestHeaderLine.of(line);
                    path = requestHeaderLine.getPath();
                }
                if (requestHeaderLine.getMethod() == null)
                    break;
                if ((requestHeaderLine.getMethod() == HttpMethod.GET) &&
                        (requestHeaderLineType == RequestHeaderLineType.EMPTY_LINE ||
                                requestHeaderLineType == RequestHeaderLineType.OTHER_LINE))
                    break;
                if ((requestHeaderLine.getMethod() == HttpMethod.POST) &&
                        (requestHeaderLineType == RequestHeaderLineType.CONTENT_LENGTH_LINE))
                    contentLength = Integer.parseInt(line.split(" ")[1]);
                if ((requestHeaderLine.getMethod() == HttpMethod.POST) &&
                        (requestHeaderLineType == RequestHeaderLineType.EMPTY_LINE)) {
                    if (path.getUrl().equals("/user/create")) {
                        User user = User.of(IOUtils.readData(br, contentLength));
                        DataBase.addUser(user);
                    }
                    if (path.getUrl().equals("/user/login")) {
                        User user = User.of(IOUtils.readData(br, contentLength));
                        logger.debug("login user : {}", user.toString());
                        User findUser = DataBase.findUserById(user.getUserId());
                        logger.debug("findUser : {}", findUser);
                        if (findUser == null) {
                            logger.debug("로그인 실패");
                        }
                        if (findUser != null && findUser.getPassword().equals(user.getPassword())) {
                            logger.debug("로그인 성공");
                            isLoginSuccess = true;
                        }
                    }
                    break;
                }
                line = br.readLine();
            }
            logger.debug("Path URL : {}", path.getUrl());
            DataOutputStream dos = new DataOutputStream(out);
            if (path.getUrl().equals("/user/create")) {
                response302Header(dos);
                return;
            }
            if (path.getUrl().equals("/user/login")) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates"+"/user/login_failed.html");
                if (isLoginSuccess) {
                    body = FileIoUtils.loadFileFromClasspath("./templates"+"/index.html");
                }
                response200Header(dos, body.length);
                responseBody(dos, body);
                return;
            }
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates"+path.getUrl());
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error("IOException : {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Exception : {}", e.getMessage());
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

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: " + "/index.html" + "\r\n");
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
