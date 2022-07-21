package model;

public class WebProtocol {
    private String type;
    private String version;

    public WebProtocol(String type, String version) {
        this.type = type;
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }
}