package endpoint;

import com.google.common.io.Files;
import utils.TemplatePageLoader;

public class TemplateResource {
    public static final TemplateResource EMPTY = new TemplateResource("", new byte[]{});
    private String filename;
    private byte[] fileBytes;

    public TemplateResource(String filename, byte[] fileBytes) {
        this.filename = filename;
        this.fileBytes = fileBytes;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public String fileExtensionName() {
        return Files.getFileExtension(filename);
    }

    public static final TemplateResource NOT_FOUND_PAGE = TemplatePageLoader.notFoundPage();


}
