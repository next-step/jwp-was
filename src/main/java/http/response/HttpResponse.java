package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class HttpResponse {

    private static final String BASIC_PROTOCOL = "HTTP/1.1";
    private static final String BASIC_REQUEST = "%s %s \r\n";
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dataOutputStream;
    private final ResponseHeader responseHeader;

    public HttpResponse(final DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
        this.responseHeader = new ResponseHeader();
    }

    private void applyHeader() throws IOException {
        for (final Map.Entry<String, String> entry : responseHeader.getHeader().entrySet()) {
            dataOutputStream.writeBytes(entry.getKey() + ": " + entry.getValue() + "\r\n");
        }
        dataOutputStream.writeBytes("\r\n");
    }

    public void addHeader(String key, String value) {
        responseHeader.addHeader(key, value);
    }

    public void forward(String path) {
        try {
            responseOK();
            responseHeader.addHeader("Host", "localhost:8080");
            responseHeader.addHeader("Accept", "text/css,*/*;q=0.1");
            responseHeader.addHeader("Connection", "keep-alive");
            applyHeader();
            responseBody(readFile("./static/" + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void forwardBody(String path) {
        try {
            responseOK();
            responseHeader.addHeader("Host", "localhost:8080");
            responseHeader.addHeader("Content-Type", ResponseContentType.HTML.toString());
            applyHeader();
            responseBody(readFile("./templates/" + path));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendRedirect(String path) {
        try {
            responseFound();
            responseHeader.addHeader("Content-Type", ResponseContentType.HTML.toString());
            responseHeader.addHeader("Location", path);
            applyHeader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void response200Header(int lengthOfBodyContent) {
        try {
            responseOK();
            responseHeader.addHeader("Content-Type", ResponseContentType.HTML.toString());
            responseHeader.addHeader("Content-Length", String.valueOf(lengthOfBodyContent));
            applyHeader();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseOK() throws IOException {
        dataOutputStream.writeBytes(String.format(BASIC_REQUEST, BASIC_PROTOCOL, ResponseHttpStatus.OK.toString()));
    }

    private void responseFound() throws IOException {
        dataOutputStream.writeBytes(String.format(BASIC_REQUEST, BASIC_PROTOCOL, ResponseHttpStatus.Found.toString()));
    }

    private byte[] readFile(String filePath) {
        try {
            return viewByUserForm(filePath);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            throw new IllegalArgumentException("Not Found Path");
        }
    }

    private byte[] viewByUserForm(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(path);
    }
}
