package webserver.http.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpResponseStatus {
    OK("200", "OK"),
    FOUND("302", "FOUND");

    private String code;
    private String description;
}
