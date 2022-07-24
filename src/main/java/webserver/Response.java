package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.ContentType;
import webserver.http.Header;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.stream.Stream;

import static model.Constant.EXTENSION_SPERATOR;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private static final String RESOURCES_TEMPLATES = "./templates";
    private static final String RESOURCES_STATIC = "./static";

    private DataOutputStream response;
    private Header header;

    public Response(OutputStream out, String path) {
        this.response = new DataOutputStream(out);
        this.header = new Header(getContentType(path));
    }

    private Map<String, String> getContentType(String path) {
        return Map.of("Content-Type", Stream.of(ContentType.values())
                .filter(type -> path.endsWith(type.getExtension()))
                .findFirst().get().getValue());
    }

    public byte[] getBody(String path) {
        byte[] body;
        try {
            return FileIoUtils.loadFileFromClasspath(getPrefix(path) + path);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private String getPrefix(String path) {
        String extension = path.substring(path.lastIndexOf(EXTENSION_SPERATOR) + 1);
        if (StringUtils.equals(extension, ContentType.HTML.getExtension()) || StringUtils.equals(extension, "ico")) {
            return RESOURCES_TEMPLATES;
        }
        return RESOURCES_STATIC;
    }

    public DataOutputStream getResponse() {
        return response;
    }

    public Header getHeader() {
        return header;
    }
}
