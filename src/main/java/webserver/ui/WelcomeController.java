package webserver.ui;

import org.springframework.http.HttpMethod;
import webserver.domain.DefaultView;
import webserver.domain.HttpRequest;
import webserver.domain.RequestMapping;
import webserver.domain.ResponseEntity;

/**
 * 기본 컨트롤러
 */
public class WelcomeController implements Controller {

    @RequestMapping(value = "/index.html", method = HttpMethod.GET)
    public ResponseEntity<DefaultView> index(HttpRequest httpRequest) {

        return ResponseEntity.ok()
                .htmlHeader()
                .body(DefaultView.createDefaultHtmlView("/index"));
    }
}
