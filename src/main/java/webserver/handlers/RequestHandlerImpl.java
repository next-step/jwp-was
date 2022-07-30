package webserver.handlers;

import webserver.domain.ContentType;
import webserver.domain.DefaultView;
import webserver.domain.HttpHeaders;
import webserver.domain.HttpRequest;
import webserver.domain.Path;
import webserver.domain.RequestLine;
import webserver.domain.ResponseEntity;
import webserver.ui.Controller;

public class RequestHandlerImpl implements RequestHandler {

    private final ControllerContainer controllerContainer;

    public RequestHandlerImpl(ControllerContainer controllerContainer) {
        this.controllerContainer = controllerContainer;
    }

    @Override
    public ResponseEntity<?> handle(HttpRequest httpRequest) {
        if (isResourceRequest(httpRequest.getRequestLine())) {
            return resourceHandle(httpRequest);
        }

        if (controllerContainer.support(httpRequest.getRequestLine())) {
            Controller controller = controllerContainer.findController(httpRequest.getRequestLine());

            return controller.execute(httpRequest);
        }

        return ResponseEntity.notFound().build();
    }

    private boolean isResourceRequest(RequestLine requestLine) {
        Path path = requestLine.getPath();

        return ContentType.isResourceContent(path.getPathStr());
    }

    private ResponseEntity<?> resourceHandle(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Path path = requestLine.getPath();
        ContentType contentType = ContentType.suffixOf(path.getPathStr());

        return ResponseEntity.ok()
                .headers(HttpHeaders.CONTENT_TYPE, contentType.getContentType())
                .body(new DefaultView(contentType.prefix(), path.getPathStr(), DefaultView.STRING_EMPTY));
    }
}
