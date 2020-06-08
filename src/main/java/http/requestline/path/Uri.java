package http.requestline.path;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Uri {

    private static final String FILE_EXTENSION_DELIMITER = "\\.";

    private final String value;

    public boolean hasExtension() {
        String extensionName = extractFileExtension();

        return FileExtension.isFileExtension(extensionName);
    }

    public String getFilePath() {
        return findFileExtension()
                .map(fileExtension -> fileExtension.getFilePath(value))
                .orElse(value);
    }

    public Optional<FileExtension> findFileExtension() {
        String extensionName = extractFileExtension();

        return FileExtension.find(extensionName);
    }

    private String extractFileExtension() {
        String[] tokens = value.split(FILE_EXTENSION_DELIMITER);
        return tokens[tokens.length - 1];
    }

    public String getMimeType() {
        return findFileExtension()
                .map(FileExtension::getMimeType)
                .get();
    }
}
