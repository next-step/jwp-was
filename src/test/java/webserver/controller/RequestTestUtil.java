package webserver.controller;

import webserver.http.request.HttpRequest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestTestUtil {
    public static HttpRequest readTestRequest(String path) throws IOException {
        InputStream in = new FileInputStream("./src/test/resources/" + path);
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        return HttpRequest.of(br);
    }
}
