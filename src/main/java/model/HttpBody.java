package model;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpBody {

    private final String CONTENT_LENGTH_KEY = "Content-Length";
    private final String contents;

    public HttpBody(BufferedReader bufferedReader, HttpHeaders requestHeaders) throws IOException {
        this.contents = this.parseBody(bufferedReader, requestHeaders);
    }

    public String getContents() {
        return contents;
    }

    private String parseBody(BufferedReader bufferedReader, HttpHeaders requestHeaders) throws IOException {
        if (bufferedReader == null) {
            return null;
        }

        String contentLengthValue = requestHeaders
                .getHeaders()
                .get(CONTENT_LENGTH_KEY);
        int contentLength = this.getContentLength(contentLengthValue);
        if (contentLength == 0) {
            return null;
        }

        return IOUtils.readData(bufferedReader, contentLength);
    }

    private int getContentLength(String contentLengthValue) {
        if (contentLengthValue != null) {
            return Integer.parseInt(contentLengthValue);
        }

        return 0;
    }

}
