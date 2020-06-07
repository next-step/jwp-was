package webserver;

import http.Cookies;
import http.Headers;
import http.parser.RequestHeaderParser;
import http.parser.RequestLineParser;
import http.request.HttpRequest;
import http.request.RequestLine;
import http.response.HttpResponse;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.controller.Controller;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
            HttpRequest httpRequest = readRequest(in);
            HttpResponse httpResponse = new HttpResponse();

            String path = httpRequest.getPath();
            Controller controller = FrontController.controllerMapping(path);
            controller.service(httpRequest, httpResponse);

            DataOutputStream dos = new DataOutputStream(out);
            writeResponse(dos, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponse(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            writeResponseLine(dos, httpResponse);
            writeCookie(dos, httpResponse);
            writeHeader(dos, httpResponse);
            writeResponseBody(dos, httpResponse);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponseLine(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.writeBytes("HTTP/1.1 " + httpResponse.getStatusCode() + " FOUND \r\n");
    }

    private void writeHeader(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        for (Iterator it = httpResponse.getHeaders().iterator(); it.hasNext(); ) {
            String headerName = (String) it.next();
            String headerValue = httpResponse.getHeader(headerName);
            dos.writeBytes(headerName + ": " + headerValue + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private void writeCookie(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        Cookies cookies = httpResponse.getCookie();
        if (!cookies.isEmpty()) {
            dos.writeBytes("Set-Cookie: " + cookies.stringify() + "\r\n");
        }
    }

    private void writeResponseBody(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
    }

    private HttpRequest readRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        String requestLineStr = br.readLine();
        logger.debug("Request Line :: {}", requestLineStr);
        RequestLine requestLine = RequestLineParser.parse(requestLineStr);

        List<String> headerList = new ArrayList<>();
        String headerStr = br.readLine();
        while (!headerStr.equals("")) {
            logger.debug("Header :: {}", headerStr);
            headerList.add(headerStr);
            headerStr = br.readLine();
        }
        Headers headers = RequestHeaderParser.parse(headerList);

        String contentLengthStr = headers.getValue("Content-Length");
        int contentLength = 0;
        if (!Strings.isBlank(contentLengthStr)) {
            contentLength = Integer.parseInt(contentLengthStr);
        }

        String requestBody = IOUtils.readData(br, contentLength);
        logger.debug("Body :: {}", requestBody);

        return new HttpRequest(requestLine, headers, requestBody);
    }
}
