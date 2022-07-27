package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import utils.FileIoUtils;
import webserver.WebApplicationServer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public enum FileResponse {

    HTML(Constants.HTTP_SUFFIX, path -> new HttpResponse(HttpStatus.OK, getBody(path))),
    CSS(Constants.CSS_SUFFIX, path -> {
        return getHttpResponse(path, Constants.CSS_CONTENT_TYPE);
    }),
    JS(Constants.JS_SUFFIX, path -> {
        return getHttpResponse(path, Constants.JS_CONTENT_TYPE);
    });

    private static class Constants { // 정적 멤버 클래스
        private static final Logger logger = LoggerFactory.getLogger(FileResponse.class);
        private static final String HTTP_SUFFIX = ".html";
        private static final String CSS_SUFFIX = ".css";
        private static final String JS_SUFFIX = ".js";
        private static final String CONTENT_TYPE_HEADER = "Content-Type";
        private static final String CSS_CONTENT_TYPE = "text/css";
        private static final String JS_CONTENT_TYPE = "text/javascript";
    }

    String suffix;
    Function<String, HttpResponse> responseFunction;

    FileResponse(String suffix, Function<String, HttpResponse> responseFunction) {
        this.suffix = suffix;
        this.responseFunction = responseFunction;
    }

    private static byte[] getBody(String path) {
        try {
            return FileIoUtils.loadFileFromClasspath(path);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private static HttpResponse getHttpResponse(String path, String jsContentType) {
        HttpResponse response = new HttpResponse(HttpStatus.OK, getBody(path));
        response.getHeader().set(Constants.CONTENT_TYPE_HEADER, jsContentType);
        return response;
    }

    public static Optional<HttpResponse> redirect(String requestPath) {
        Constants.logger.debug("pathname = {}", requestPath);
        return Arrays.stream(FileResponse.values())
                .filter(value -> requestPath.endsWith(value.suffix))
                .map(value -> value.responseFunction.apply(requestPath))
                .findAny();
    }
}
