package http;

public class RequestHeader {
    private String key;
    private String value;

    public RequestHeader(String key, String value) {
        System.out.println("key: " + key + ", value: " + value);
        this.key = key.trim();
        this.value = value.trim();
    }

    public RequestHeader(String requestLine) {
        if (requestLine.contains(":")) {
            String[] tokens = requestLine.split(":");
            this.key = tokens[0].trim();
            this.value = tokens[1].trim();

            System.out.println("key: " + key + ", value: " + value);
        }
    }
}
