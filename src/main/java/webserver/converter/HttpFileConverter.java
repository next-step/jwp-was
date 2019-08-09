package webserver.converter;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpFileConverter extends HttpConverter{

    public static String readFile(String path)
            throws IOException, URISyntaxException {
        return new String(FileIoUtils
                    .loadFileFromClasspath(
                        HttpFileResource
                                .getFileBaseDirectory(path)));
    }

}
