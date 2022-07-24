package webserver.service;

import java.net.Socket;

import webserver.response.post.PostUserCreateResponse;

public class ResponsePostHandler {
    private final Socket connection;

    public ResponsePostHandler(Socket connection) {
        this.connection = connection;
    }

    public void run(String requestBody) {
        PostUserCreateResponse response = new PostUserCreateResponse();
        response.response(requestBody);
    }
}
