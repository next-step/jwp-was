package webserver.converter;

import utils.FileIoUtils;
import webserver.domain.HttpEntity;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpFileConverter extends HttpConverter{

    public static void readFile(HttpEntity httpEntity) throws IOException, URISyntaxException {
        httpEntity.setReturnContent(
                new String(FileIoUtils
                        .loadFileFromClasspath(
                                HttpFileResource.getFileBaseDirectory(httpEntity.getUrlPath()))));
    }

}
