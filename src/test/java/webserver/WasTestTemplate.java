package webserver;

import http.request.HttpRequest;

import java.io.*;

public final class WasTestTemplate {

    private String testDirectory = "./src/test/resources/";
    private String outputDirectory = "./out/";

    public HttpRequest request(String fileName) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + fileName));
        return HttpRequest.from(in);
    }

    public OutputStream createOutputStream(String fileName) throws FileNotFoundException {
        createOutputDirectoryDontExist();
        return new FileOutputStream(new File(outputDirectory + fileName));
    }

    private void createOutputDirectoryDontExist() {
        File directory = new File(outputDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
