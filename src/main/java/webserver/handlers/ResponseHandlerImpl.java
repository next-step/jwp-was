package webserver.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.ResponseEntity;
import webserver.resolvers.Resolver;
import webserver.resolvers.ResolverContainer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ResponseHandlerImpl implements ResponseHandler {
    private final ResolverContainer resolverContainer;

    public ResponseHandlerImpl(ResolverContainer resolverContainer) {
        this.resolverContainer = resolverContainer;
    }

    @Override
    public Void handle(ResponseEntity<?> responseEntity) throws IOException {
        try (OutputStream out = connection.getOutputStream();
             BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(out))) {

            Object body = responseEntity.getBody();

            Resolver resolver = resolverContainer.find(body);
            String resultBody = resolver.resolve(body);
            ResponseEntity<?> convertedResponseEntity = responseEntity.newInstanceFromBody(resultBody);

            wr.write(convertedResponseEntity.toString());
            wr.flush();

            return null;

        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    private Socket connection;

    @Override
    public ResponseHandler changeConnection(Socket conn) {
        this.connection = conn;

        return this;
    }
}
