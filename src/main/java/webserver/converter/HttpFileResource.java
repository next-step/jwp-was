package webserver.converter;

import java.nio.file.InvalidPathException;
import java.util.Arrays;

public enum HttpFileResource {

    HTML(".html", "./templates"),
    ICO(".ico", "./templates"),
    JS(".js", "./static"),
    WOFF(".woff", "./static"),
    TTF(".ttf", "./static"),
    CSS(".css", "./static");

    private String fileExtension;
    private String filePath;
    HttpFileResource(String file, String path){
        this.fileExtension = file;
        this.filePath = path;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getFilePath() {
        return filePath;
    }

    public static String getFileBaseDirectory(String filePath){
        String BaseDirectoryStr = Arrays.stream(HttpFileResource.values())
                .filter(httpFileResource -> filePath.contains(httpFileResource.getFileExtension()))
                .findFirst()
                .orElseThrow(() -> new InvalidPathException(filePath, "지원하지 않는 형식의 파일입니다."))
                .getFilePath();

        return BaseDirectoryStr + filePath;
    }
}
