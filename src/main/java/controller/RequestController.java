package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Cookie;
import request.Request;
import service.RequestService;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class RequestController {

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private final Request request;
    private final RequestService requestService;

    public RequestController(Request request, RequestService requestService) {
        this.request = request;
        this.requestService = requestService;
    }

    public void handle(OutputStream out) throws IOException, URISyntaxException {

        byte[] body = new byte[0];
        DataOutputStream dos = new DataOutputStream(out);

        logger.info("request path : {} ",request.getRequestPath());
        if (request.requestPathCheck("/user/create")) {
            requestService.saveMember();
        }
        Cookie cookie = null;
        if (request.requestPathCheck("/user/login")) {
            cookie = requestService.checkIdAndPassword(request.convertUserOfQueryParam());
        }

        if (FileIoUtils.isLastEndWithHtml(request.getRequestPath())) {
            body = FileIoUtils.loadFileFromClasspath(request.getRequestPath());
            response200Header(dos, body.length);
        } else {
//            response302Header(dos);
            response302HeaderWithCookie(dos, cookie);
        }
        responseBody(dos, body);
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
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: http://localhost:8080/index.html" + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302HeaderWithCookie(DataOutputStream dos, Cookie cookie){
        try{
            dos.writeBytes("HTTP/1.1 302 OK \r\n");
            dos.writeBytes("Set-Cookie:"+ cookie +" \r\n");
            dos.writeBytes("Location : http://localhost:8080/index.html" +"\r\n");
            dos.writeBytes("\r\n");
        }
        catch (Exception e){
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
