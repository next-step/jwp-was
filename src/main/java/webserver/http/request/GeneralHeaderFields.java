package webserver.http.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 4.5 General Header Fields
 * There are a few header fields which have general applicability for
 * both request and response messages, but which do not apply to the
 * entity being transferred. These header fields apply only to the
 * message being transmitted.
 *
 *     general-header = Cache-Control            ; Section 14.9
 *                    | Connection               ; Section 14.10
 *                    | Date                     ; Section 14.18
 *                    | Pragma                   ; Section 14.32
 *                    | Trailer                  ; Section 14.40
 *                    | Transfer-Encoding        ; Section 14.41
 *                    | Upgrade                  ; Section 14.42
 *                    | Via                      ; Section 14.45
 *                    | Warning                  ; Section 14.46
 */
@Getter
@AllArgsConstructor
public enum GeneralHeaderFields {

    CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection"),
    DATE("Date"),
    PRAGMA("Pragma"),
    TRAILER("Trailer"),
    TRANSFER_ENCODING("Transfer-Encoding"),
    UPGRADE("Upgrade"),
    VIA("Via"),
    WARNING("Warning")
    ;

    private final String field;
}
