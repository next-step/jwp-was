package http;

import java.io.*;

public class HttpRequest {

    private RequestLine requestLine;

    public HttpRequest(InputStream inputStream) throws IOException {
        String requestLine;
        do {
            requestLine = readLine(inputStream);
            if (requestLine == null) {
                return;
            }
        } while (requestLine.equals(""));

        this.requestLine = RequestLineParser.parse(requestLine);
    }

    private String readLine(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        return bufferedReader.readLine();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }
}
