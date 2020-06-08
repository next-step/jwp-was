package http.requestline.path;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

import static http.requestline.path.FileExtension.FilePrefix.DEFAULT_FILE_PREFIX;
import static http.requestline.path.FileExtension.FilePrefix.STATIC_FILE_PREFIX;

@Getter
@RequiredArgsConstructor
public enum FileExtension {

    HTML("html", DEFAULT_FILE_PREFIX, "text/html"),
    CSS("css", STATIC_FILE_PREFIX, "text/css"),
    JS("js", STATIC_FILE_PREFIX, "application/js"),
    ICO("ico", DEFAULT_FILE_PREFIX, "image/x-icon"),
    WOFF("woff", STATIC_FILE_PREFIX, "application/x-font-woff");

    private final String extension;
    private final String filePrefix;
    private final String mimeType;

    public static boolean isFileExtension(String text) {
        return Arrays.stream(values())
                .map(FileExtension::getExtension)
                .anyMatch(extension -> extension.equals(text));
    }

    public static Optional<FileExtension> find(String extensionName) {
        return Arrays.stream(values())
                .filter(fileExtension -> fileExtension.extension.equals(extensionName))
                .findAny();
    }

    public String getFilePath(String path) {
        return filePrefix + path;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static class FilePrefix {
        public static final String DEFAULT_FILE_PREFIX = "./templates";
        public static final String STATIC_FILE_PREFIX = "./static";
    }
}
