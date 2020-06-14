package webserver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

@Getter
@AllArgsConstructor
public class ResponseBody {

    private static final String STATIC_FILE_ROOT_LOCATION = "./templates";

    private byte[] file;
    private FileType fileType;

    public static ResponseBody of(RequestLine requestLine) throws IOException, URISyntaxException {
        FileType fileType = requestLine.getFileType();
        String path = fileType.getLocation() + requestLine.getUrl();

        byte[] file = FileIoUtils.loadFileFromClasspath(path);
        return new ResponseBody(file, fileType);
    }

    public int getLength() {
        return file.length;
    }

    public String getContentType() {
        return fileType.getContentType();
    }
}
