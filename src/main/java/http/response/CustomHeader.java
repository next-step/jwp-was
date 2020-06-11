package http.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomHeader {
    private Map<String, String> customHeader;

    public CustomHeader() {
        this.customHeader = new HashMap<>();
    }

    public void add(String key, String value) {
        this.customHeader.put(key, value);
    }

    public String get(String key) {
        return this.customHeader.get(key);
    }

    public List<String> getList() {
        return customHeader.entrySet().stream()
                .map((header -> header.getKey().concat(": ").concat(header.getValue())))
                .collect(Collectors.toList());
    }
}
