package view;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileView extends AbstractView {

    private static final String TEMPLATE_PATH = "./templates";

    private final String file;

    public FileView(String file) {
        this.file = file;
    }

    public byte[] read(HttpRequest request, HttpResponse response) {
        try {
            return FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + file);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

}
