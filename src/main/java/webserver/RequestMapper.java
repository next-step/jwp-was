package webserver;

import webserver.view.LoginHandler;
import webserver.view.ResourceHandler;
import webserver.view.UserCreateHandler;
import webserver.view.UserListHandler;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum RequestMapper {

    USER_CREATE("/user/create") {
        RequestMappingHandler createHandler() {
            return new UserCreateHandler();
        }
    },
    USER_LOGIN("/user/login") {
        RequestMappingHandler createHandler() {
            return new LoginHandler();
        }
    },
    USER_LIST("/user/list") {
        RequestMappingHandler createHandler() {
            return new UserListHandler();
        }
    },
    RESOURCE("resource") {
        RequestMappingHandler createHandler() {
            return new ResourceHandler();
        }
    };

    private String path;
    private static Map<String, RequestMapper> uris;
    static {
        uris = Arrays.stream(values())
                .collect(Collectors.toMap(requestMapper -> requestMapper.path, requestMapper -> requestMapper, (requestMapper1, requestMapper2) -> requestMapper1));
    }

    RequestMapper(String path) {
        this.path = path;
    }

    abstract RequestMappingHandler createHandler();

    public static RequestMappingHandler find(String path) {
        return Optional.ofNullable(uris.get(path))
                .orElse(uris.get("resource"))
                .createHandler();
    }
}
