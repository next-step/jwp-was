package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.request.StyleSheet;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

public class ViewHelper {

    private static final String TEMPLATE_PATH = "./templates";
    private static final String STATIC_PATH = "./static";
    private static final String HTML_EXTENSION = ".html";
    private static final String EXTENSION_DELIMITER = "\\.";

    public static byte[] readFile(String fileName) {
        try {
            return FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + fileName);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static byte[] readHandlebar(String location, Object data) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATE_PATH);
        loader.setSuffix(HTML_EXTENSION);
        loader.setCharset(Charset.defaultCharset());
        Handlebars handlebars = new Handlebars(loader);
        try {
            Template template = handlebars.compile(location);
            String content = template.apply(data);
            return content.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];

    }

    public static byte[] readStyleSheetFile(String file) {
        try {
            return FileIoUtils.loadFileFromClasspath(STATIC_PATH + parsing(file));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private static String parsing(String path) {
        // "/user" 가 prefix로 붙어서 제거하기 위한 parsing
        String[] files = path.split(EXTENSION_DELIMITER);
        StyleSheet styleSheet = StyleSheet.findByExtension(files[files.length - 1]);
        return path.substring(path.indexOf(styleSheet.getLocation()));
    }
}
