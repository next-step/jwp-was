package model;

import org.springframework.http.HttpStatus;

public class HttpResponse {
    private HttpStatus code;
    private ResponseHeader header;
    private byte[] body;


}
