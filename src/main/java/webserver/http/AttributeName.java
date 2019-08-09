package webserver.http;

public enum AttributeName {

    USER("User")
    ;

    private final String attributeName;

    AttributeName(final String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public String toString() {
        return attributeName;
    }
}
