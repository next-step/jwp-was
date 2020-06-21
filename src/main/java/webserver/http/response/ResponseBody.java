package webserver.http.response;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.FileType;
import webserver.ModelAndView;
import webserver.http.request.RequestLine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ResponseBody {

    private static final Logger log = LoggerFactory.getLogger(ResponseBody.class);

    private static final String STATIC_FILE_ROOT_LOCATION = "./templates";

    private byte[] file = "".getBytes();
    private FileType fileType;

    public static ResponseBody of(RequestLine requestLine) throws IOException, URISyntaxException {
        FileType fileType = requestLine.getFileType();
        if (fileType.equals(FileType.NONE)) {
            return new ResponseBody();
        }
        String path = fileType.getLocation() + requestLine.getUrl();
        byte[] file = FileIoUtils.loadFileFromClasspath(path);
        return new ResponseBody(file, fileType);
    }

    public static ResponseBody of(ModelAndView mav) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.addBody(mav);
        return responseBody;
    }

    private void addBody(ModelAndView mav) {
        Map<String, Object> model = mav.getModel();
        String view = mav.getView();

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates/");
        loader.setSuffix("");
        Handlebars handlebars = new Handlebars(loader);
        String userListPage;
        try {
            Template template = handlebars.compile(view);
            userListPage = template.apply(model);
            addFile(userListPage.getBytes());
        } catch (Exception e) {
            log.debug("handlebars error : {}", e.getMessage());
        }
    }

    private void addFile(byte[] file) {
        this.file = file;
        this.fileType = FileType.HTML;
    }

    public String getLength() {
        return String.valueOf(file.length);
    }

    public String getContentType() {
        return fileType.getContentType();
    }

    public void response(DataOutputStream dos) {
        if (Objects.isNull(file)) {
            return;
        }

        try {
            dos.write(file, 0, file.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
