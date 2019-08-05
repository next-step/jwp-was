package controller;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import webserver.http.HttpMethod;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class RequestMapping {

    private String path;
    private HttpMethod httpMethod;
}
