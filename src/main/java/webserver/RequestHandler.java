package webserver;

import exceptions.MappingNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpBaseRequest;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.mapper.RequestMappers;
import webserver.resolvers.body.BodyResolvers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final String TEMPLATE_ROOT = "./templates";

    private Socket connection;
    
    private RequestMappers requestMappers;
    
    private BodyResolvers bodyResolvers;

    public RequestHandler(Socket connectionSocket, RequestMappers requestMappers, BodyResolvers bodyResolvers) {
        this.connection = connectionSocket;
        this.requestMappers = requestMappers;
        this.bodyResolvers = bodyResolvers;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = bodyResolvers.resoveByMatchResolver(HttpBaseRequest.parse(in));
            HttpResponse httpResponse = HttpResponse.of(httpRequest, out);

            if(httpRequest.getSession(false) == null) {
                httpRequest.getSession(true);
            }

            if("/".equals(httpRequest.getPath())) {
            	httpResponse.sendRedirect("/index.html");
            	httpResponse.writeResponse();
            	return;
            }
            
            try {
            	requestMappers.matchHandle(httpRequest, httpResponse);	
            } catch(MappingNotFoundException e) {
            	httpResponse.send404();	
            }
            httpResponse.writeResponse();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}
