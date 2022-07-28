package utils;

import endpoint.TemplateResource;

public class StaticResourceLoader {
    public static final byte[] EMPTY_RESOURCE_BYTES = new byte[]{};
    private static final String STATIC_RESOURCE_BASE_PATH = "./static";

    public static TemplateResource getStaticResource(String resourceFilename) {
        try {
            byte[] templatePageBytes = FileIoUtils.loadFileFromClasspath(STATIC_RESOURCE_BASE_PATH + resourceFilename).orElse(EMPTY_RESOURCE_BYTES);
            return new TemplateResource(resourceFilename, templatePageBytes);
        } catch (Exception e) {
            return TemplateResource.EMPTY;
        }
    }
}
