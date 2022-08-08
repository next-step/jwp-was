package webserver.http.response;

public class View {

    private final String filePath;

    public View(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
