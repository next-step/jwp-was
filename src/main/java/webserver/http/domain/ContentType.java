package webserver.http.domain;

import webserver.http.domain.exception.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Objects;

public enum ContentType {
    CSS("css", "text/css"),
    JAVASCRIPT("js", "text/javascript"),
    JSON("js", "text/json"),
    HTML("html", "text/html"),
    PNG("png", "image/png"),
    ICO("ico", "image/vnd.microsoft.icon"),
    EOT("eot", "font/eot"),
    SVG("svg", "font/svg"),
    TTF("ttf", "font/ttf"),
    WOFF("woff", "font/woff"),
    WOFF2("woff2", "font/woff2");

    private static final String FILE_EXTENSION_DELIMITER = ".";

    private final String fileExtension;
    private final String header;

    ContentType(String fileExtension, String header) {
        this.fileExtension = fileExtension;
        this.header = header;
    }

    public static ContentType from(String resourceFilePath) {
        int startIndexOfFileExtension = resourceFilePath.lastIndexOf(FILE_EXTENSION_DELIMITER) + 1;
        String fileExtension = resourceFilePath.substring(startIndexOfFileExtension);

        return Arrays.stream(values())
                .filter(contentType -> Objects.equals(contentType.fileExtension, fileExtension))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("지원하지 않는 리소스입니다."));
    }

    public String getHeader() {
        return header;
    }
}
