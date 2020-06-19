package webserver;

import http.request.Uri;
import http.response.ContentType;
import utils.FileIoUtils;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.EnumSet;

public class StaticResourceLoader {

    public static final String TEMPLATE_PATH = "./templates";
    public static final String STATIC_PATH = "./static";
    private static final EnumSet<ContentType> typesInTemplates = EnumSet.of(ContentType.HTML, ContentType.ICO);

    public static byte[] loadResource(String location) throws IOException {
        Uri uri = Uri.from(location);
        try {
            return FileIoUtils.loadFileFromClasspath(getRelativePath(uri));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("invalid uri");
        }
    }

    private static String getRelativePath(Uri uri) {
        String suffix = uri.getExtension();
        String prefix = typesInTemplates.contains(ContentType.from(suffix)) ? TEMPLATE_PATH : STATIC_PATH;
        return prefix + uri.getPath();
    }

}
