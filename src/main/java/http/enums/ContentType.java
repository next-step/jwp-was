package http.enums;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created By kjs4395 on 2020-06-09
 */
public enum ContentType {
    NONE("application/octet-stream", "", ""),
    CSS("text/css", "/css", "./static"),
    FONT("application/x-font-woff", "/font","./static"),
    HTML("text/html", "","./templates"),
    IMAGES("image/png", "/images","./static"),
    JS("text/javascript", "/js", "./static");

    String mimeType;
    String resourceFolderName;
    String resourcePath;

    ContentType(String mimeType, String resourceFolderName, String resourcePath) {
        this.mimeType = mimeType;
        this.resourceFolderName = resourceFolderName;
        this.resourcePath = resourcePath;
    }

    public boolean isMatchFolder(String path) {
        if(StringUtils.isEmpty(this.resourceFolderName)) {
            return false;
        }
        return path.startsWith(resourceFolderName);
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public static ContentType findContentType(String requestPath) {
        Optional<ContentType> contentTypeOptional = Arrays.stream(ContentType.values())
                .filter(contentType -> contentType.isMatchFolder(requestPath))
                .findFirst();
        return contentTypeOptional.orElseGet(() -> requestPath.matches("[^.]*.html[^.]*") ? ContentType.HTML : ContentType.NONE);
    }
}
