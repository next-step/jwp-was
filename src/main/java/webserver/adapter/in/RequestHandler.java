package webserver.adapter.in;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.adapter.out.web.HttpResponse;
import webserver.application.UserProcessor;
import webserver.domain.http.HttpHeader;
import webserver.domain.http.RequestBody;
import webserver.domain.http.RequestLine;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final UserProcessor userProcessor;

    public RequestHandler(Socket connectionSocket, UserProcessor userProcessor) {
        this.connection = connectionSocket;
        this.userProcessor = userProcessor;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            RequestLine requestLine = RequestLine.from(br.readLine());
            logger.debug("{}", requestLine);

            HttpHeader httpHeader = getHttpHeader(br);
            RequestBody httpBody = RequestBody.from(IOUtils.readData(br, httpHeader.getContentLength()));
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));

            String path = requestLine.getUri().getPath();

            UserController userController = new UserController(userProcessor);
            if (userController.isSupport(path)) {
                userController.handle(requestLine, httpHeader, httpBody, httpResponse);
                return;
            }

            httpResponse.responseOk(path, httpHeader.isLogined());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpHeader getHttpHeader(BufferedReader br) throws IOException {
        String line = null;
        List<String> request = new ArrayList<>();

        while (!"".equals(line)) {
            line = br.readLine();
            logger.debug("{}", line);
            request.add(line);

            if (Objects.isNull(line)) {
                break;
            }
        }

        return HttpHeader.from(request);
    }
}
