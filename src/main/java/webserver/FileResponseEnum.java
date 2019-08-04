package webserver;

import utils.FileIoUtils;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public enum  FileResponseEnum {
    HTML(FileResponseEnum.HTML_FILE_SUFFIX, requestPath -> new HttpResponse(HttpStatus.OK, getBody(requestPath, FileResponseEnum.TEMPLATE_FILE_PREFIX))),
    CSS(FileResponseEnum.CSS_FILE_SUFFIX, requestPath -> {
        HttpResponse httpResponse = new HttpResponse(HttpStatus.OK, getBody(requestPath, FileResponseEnum.STATIC_FILE_PREFIX));
        httpResponse.getHttpHeaders().set(FileResponseEnum.CONTENT_TYPE_HEADER, FileResponseEnum.CSS_CONTENT_TYPE);
        return httpResponse;
    });

    private String fileSuffix;
    private Function<String, HttpResponse> responseFunction;

    private static final String STATIC_FILE_PREFIX = "./static";
    private static final String CSS_FILE_SUFFIX = ".css";
    private static final String TEMPLATE_FILE_PREFIX = "./templates";
    private static final String HTML_FILE_SUFFIX = ".html";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String CSS_CONTENT_TYPE = "text/css";

    FileResponseEnum(String fileSuffix, Function<String, HttpResponse> responseFunction) {
        this.fileSuffix = fileSuffix;
        this.responseFunction = responseFunction;
    }

    private static byte[] getBody(String requestPath, String s) {
        try {
            return FileIoUtils.loadFileFromClasspath(s + requestPath);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static Optional<HttpResponse> getFileResponse(String requestPath) {
        return Arrays.stream(FileResponseEnum.values())
                .filter(value -> requestPath.endsWith(value.fileSuffix))
                .map(value -> value.responseFunction.apply(requestPath))
                .findAny();
    }
}
