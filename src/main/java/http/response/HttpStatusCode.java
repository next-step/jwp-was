package http.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HttpStatusCode {
    OK(200),
    CREATED(201),
    FOUND(302),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    IM_A_TEAPOT(418), // ^^!
    INTERNAL_SERVER_ERROR(500);

    private final int code;
}
