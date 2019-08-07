package webserver.converter;

import utils.FileIoUtils;
import webserver.domain.HttpEntity;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpFileConverter extends HttpConverter{

    public static String readFile(HttpEntity httpEntity)
            throws IOException, URISyntaxException {
        return new String(FileIoUtils
                    .loadFileFromClasspath(
                        HttpFileResource
                                .getFileBaseDirectory(httpEntity.getUrlPath())));
    }

}
