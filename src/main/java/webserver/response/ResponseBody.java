package webserver.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import utils.FileIoUtils;
import webserver.FileType;
import webserver.request.RequestLine;

import java.io.IOException;
import java.net.URISyntaxException;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ResponseBody {

    private static final String STATIC_FILE_ROOT_LOCATION = "./templates";

    private byte[] file;
    private FileType fileType;

    public static ResponseBody of(RequestLine requestLine) throws IOException, URISyntaxException {
        FileType fileType = requestLine.getFileType();
        if (fileType.equals(FileType.NONE)) {
            return new ResponseBody();
        }
        String path = fileType.getLocation() + requestLine.getUrl();
        byte[] file = FileIoUtils.loadFileFromClasspath(path);
        return new ResponseBody(file, fileType);
    }

    public String getLength() {
        return String.valueOf(file.length);
    }

    public String getContentType() {
        return fileType.getContentType();
    }
}
