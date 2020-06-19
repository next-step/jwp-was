package http;

import javax.annotation.Nonnull;

import org.springframework.util.StringUtils;

public class MimeTypeUtil {
    private MimeTypeUtil() {
    }

    @Nonnull
    public static MimeType findMimeTypeByPath(@Nonnull String path) {
        String filenameExtension = StringUtils.getFilenameExtension(path);

        if ("css".equalsIgnoreCase(filenameExtension)) {
            return MimeType.TEXT_CSS;
        }

        if ("js".equalsIgnoreCase(filenameExtension)) {
            return MimeType.APPLICATION_JAVASCRIPT;
        }

        if ("jpg".equalsIgnoreCase(filenameExtension)) {
            return MimeType.IMAGE_JPEG;
        }

        if ("html".equalsIgnoreCase(filenameExtension)) {
            return MimeType.TEXT_HTML;
        }

        return MimeType.APPLICATION_JSON;
    }
}
