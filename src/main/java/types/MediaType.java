package types;

public enum MediaType {

    TEXT_HTML("text/html"), TEXT_CSS("text/css"), APPLICATION_JSON("application/json");

    private final String value;

    MediaType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
