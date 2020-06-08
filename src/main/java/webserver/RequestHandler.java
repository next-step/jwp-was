package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.QueryStrings;
import http.RequestLineParser;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line = br.readLine();
            logger.debug("requestline : {}",line);

            HttpRequest httpRequest = new HttpRequest(RequestLineParser.parse(line));

            String path = httpRequest.getPath();
            int contentLength = 0;
            boolean loginStatus = false;
            while(!line.equals("")) {
                line = br.readLine();
                logger.debug("header : {}", line);
                if(line.contains("Content-Length")) {
                    String value = line.split(" ")[1];
                    contentLength = Integer.parseInt(value);
                }
                if(line.contains("Cookie")) {
                    loginStatus = loginCheck(line);
                }
            }

            if(path.equals("/user/create")) {
                String body = IOUtils.readData(br, contentLength);

                QueryStrings queryStrings = new QueryStrings(body);

                User user = new User(queryStrings.getParameter("userId"),
                        queryStrings.getParameter("password"), queryStrings.getParameter("name"),
                        queryStrings.getParameter("email"));
                /*User user = new User(httpRequest.getParameter("userId"),
                        httpRequest.getParameter("password"), httpRequest.getParameter("name"),
                        httpRequest.getParameter("email"));*/
                logger.debug("User : {}", user);
                DataBase.addUser(user);
                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "/index.html" );
            } else if(path.endsWith("css")){
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = HttpResponse.getCss(path);
                responseCssHeader(dos, body.length);
                responseBody(dos, body);
            } else if(path.equals("/user/login")) {
                String body = IOUtils.readData(br, contentLength);

                QueryStrings queryStrings = new QueryStrings(body);

                User user = DataBase.findUserById(queryStrings.getParameter("userId"));

                if(user == null) {
                    responseResource(out, path);
                    return;
                }

                if(user.getPassword().equals(queryStrings.getParameter("password"))) {
                    DataOutputStream dos = new DataOutputStream(out);
                    responseLoginHeader(dos);
                } else {
                    responseResource(out, path);
                }

            } else if(path.equals("/user/list")) {
                if(!loginStatus) {
                    responseResource(out, "/login.html");
                    return;
                }
                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix("/templates");
                loader.setSuffix(".html");
                Handlebars handlebars = new Handlebars(loader);

                Template template = handlebars.compile("user/list");
                Collection<User> users = DataBase.findAll();

                String profilePage = template.apply(users);

                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = profilePage.getBytes();
                response200Header(dos, body.length);
                responseBody(dos, body);
            } else{
                responseResource(out, path);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean loginCheck(String line) {
        boolean result = false;
        String loginStatus = line.split("=")[1];

        if(loginStatus.equals("true"))
            result = true;

        return result;
    }

    private void responseResource(OutputStream out, String url) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = HttpResponse.getBody(url);
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void responseLoginHeader(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;\r\n");
            dos.writeBytes("Set-Cookie: logined=true; path=/ \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseCssHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
