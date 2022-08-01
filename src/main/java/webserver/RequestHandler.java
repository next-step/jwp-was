package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import controller.HomeController;
import controller.SignUpController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestHeader;
import webserver.http.request.RequestLine;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseBody;
import webserver.http.response.ResponseHeader;
import webserver.http.response.ResponseLine;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String END_OF_LINE = "";

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpRequest httpRequest = convertStreamToHttpRequest(in);

            HttpResponse httpResponse = route(httpRequest);

            final DataOutputStream dos = new DataOutputStream(out);
            writeResponseLine(dos, httpResponse.getResponseLine());

            final Optional<ResponseBody> responseBody = httpResponse.getResponseBody();
            if (responseBody.isEmpty()) {
                writeResponseHeader(dos, 0, httpResponse.getResponseHeader());
                return;
            }
            final byte[] body = FileIoUtils.loadFileFromClasspath(responseBody.get().getView().getFilePath());
            writeResponseHeader(dos, body.length, httpResponse.getResponseHeader());
            writeResponseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequest convertStreamToHttpRequest(InputStream is) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line = br.readLine();

        final RequestLine requestLine = RequestLine.parseFrom(line);
        logger.info(requestLine.toString());

        final RequestHeader requestHeader = new RequestHeader();
        while (! line.equals(END_OF_LINE) || line == null) {
            logger.info(line);
            line = br.readLine();
            requestHeader.add(line);
        }

        RequestBody requestBody;
        if (requestHeader.getContentLength().isEmpty()) {
            requestBody = new RequestBody(Collections.EMPTY_MAP);
        } else {
            requestBody = RequestBody.parseFrom(
                    IOUtils.readData(br, requestHeader.getContentLength().get())
            );
        }
        logger.info(requestBody.toString());

        return new HttpRequest(requestLine, requestHeader, requestBody);
    }

    private HttpResponse route(final HttpRequest httpRequest){
        final String path = httpRequest.getRequestLine().getUrl().getPath();

        if (path.equals(SignUpController.path)) {
            SignUpController signUpController = new SignUpController();
            return signUpController.run(httpRequest);
        } else if (path.equals(HomeController.path)) {
            HomeController homeController = new HomeController();
            return homeController.run(httpRequest);
        }

        return null; // TODO: server error 로 변경하기
    }

    private void writeResponseLine(final DataOutputStream dos, final ResponseLine responseLine) {
        try {
            dos.writeBytes(responseLine.toPrint());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponseHeader(final DataOutputStream dos, final int lengthOfBodyContent, ResponseHeader responseHeader){
        responseHeader.setContentLength(Integer.toString(lengthOfBodyContent));
        List<String> headersToPrint = responseHeader.toPrint();

        System.out.println(headersToPrint);
        headersToPrint.forEach(header -> {
            try {
                dos.writeBytes(header);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        });
    }

    private void writeResponseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
