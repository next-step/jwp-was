package webserver.http.config;

import webserver.http.domain.controller.Controller;
import webserver.http.domain.controller.RequestProcessor;
import webserver.http.domain.controller.RootController;
import webserver.http.domain.controller.StaticResourceController;
import webserver.http.view.CookiesParser;
import webserver.http.view.HeadersParser;
import webserver.http.view.KeyValuePairParser;
import webserver.http.view.ProtocolParser;
import webserver.http.view.request.ParametersParser;
import webserver.http.view.request.RequestLineParser;
import webserver.http.view.request.RequestReader;
import webserver.http.view.request.URIParser;
import webserver.http.view.response.ResponseWriter;

import java.util.ArrayList;
import java.util.List;

public class WebServerConfig {
    public static RequestReader requestReader() {
        KeyValuePairParser keyValuePairParser = new KeyValuePairParser();
        ParametersParser parametersParser = new ParametersParser(keyValuePairParser);
        URIParser uriParser = new URIParser(keyValuePairParser, parametersParser);
        CookiesParser cookiesParser = new CookiesParser(keyValuePairParser);

        return new RequestReader(
                new RequestLineParser(uriParser, new ProtocolParser()),
                new HeadersParser(keyValuePairParser),
                parametersParser,
                cookiesParser
        );
    }

    public static ResponseWriter responseWriter() {
        return new ResponseWriter();
    }

    public static RequestProcessor requestProcessor(List<Controller> controllers) {
        List<Controller> totalControllers = new ArrayList<>(List.of(
                new RootController(),
                new StaticResourceController()
        ));

        totalControllers.addAll(controllers);

        return new RequestProcessor(totalControllers);
    }
}
