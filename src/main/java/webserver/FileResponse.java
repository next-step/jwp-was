package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public enum FileResponse {
    HTML(FileResponse.HTML_FILE_SUFFIX, requestPath -> new HttpResponse(HttpStatus.OK, getBody(requestPath, FileResponse.TEMPLATE_FILE_PREFIX))),
    CSS(FileResponse.CSS_FILE_SUFFIX, requestPath -> {
        HttpResponse httpResponse = new HttpResponse(HttpStatus.OK, getBody(requestPath, FileResponse.STATIC_FILE_PREFIX));
        httpResponse.getHttpHeaders().set(FileResponse.CONTENT_TYPE_HEADER, FileResponse.CSS_CONTENT_TYPE);
        return httpResponse;
    });

    private String fileSuffix;
    private Function<String, HttpResponse> responseFunction;

    private static final Logger logger = LoggerFactory.getLogger(FileResponse.class);
    private static final String STATIC_FILE_PREFIX = "./static";
    private static final String CSS_FILE_SUFFIX = ".css";
    private static final String TEMPLATE_FILE_PREFIX = "./templates";
    private static final String HTML_FILE_SUFFIX = ".html";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String CSS_CONTENT_TYPE = "text/css";

    FileResponse(String fileSuffix, Function<String, HttpResponse> responseFunction) {
        this.fileSuffix = fileSuffix;
        this.responseFunction = responseFunction;
    }

    private static byte[] getBody(String requestPath, String prefix) {
        try {
            return FileIoUtils.loadFileFromClasspath(prefix + requestPath);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new byte[0];
    }

    public static Optional<HttpResponse> getFileResponse(String requestPath) {
        return Arrays.stream(FileResponse.values())
                .filter(value -> requestPath.endsWith(value.fileSuffix))
                .map(value -> value.responseFunction.apply(requestPath))
                .findAny();
    }
}
