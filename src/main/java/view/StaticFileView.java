package view;

import http.request.HttpRequest;
import http.request.StyleSheet;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticFileView extends AbstractView {

    private static final String STATIC_PATH = "./static";
    private static final String EXTENSION_DELIMITER = "\\.";

    private final String file;

    public StaticFileView(String file) {
        this.file = file;
    }

    public byte[] read(HttpRequest request, HttpResponse response) {
        try {
            return FileIoUtils.loadFileFromClasspath(STATIC_PATH + parsing(file));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private static String parsing(String path) {
        // "/user" 가 prefix로 붙어서 제거하기 위한 parsing
        String[] files = path.split(EXTENSION_DELIMITER);
        StyleSheet styleSheet = StyleSheet.findByExtension(files[files.length - 1]);
        return path.substring(path.indexOf(styleSheet.getLocation()));
    }
}
