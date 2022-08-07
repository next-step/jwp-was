package endpoint;

import com.google.common.io.Files;

public class TemplateResource {
    public static final TemplateResource EMPTY = new TemplateResource("", new byte[]{});
    private final String filename;
    private final byte[] fileBytes;

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
}
