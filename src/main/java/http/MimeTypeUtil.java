package http;

import javax.annotation.Nonnull;

public class MimeTypeUtil {
    private MimeTypeUtil() {
    }

    @Nonnull
    public static MimeType findMimeTypeByFileExtension(@Nonnull String filenameExtension) {
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
