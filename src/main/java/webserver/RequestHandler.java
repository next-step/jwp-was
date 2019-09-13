package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;
import utils.FileIoUtils;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }


    /*
        2단계
        - 요구사항1 : O
        - 요구사항2 : O
        - 요구사항3 : O
        - 요구사항4 : O
        - 요구사항5 : X (cookie의 값이 계속 true로 나옴)
        - 요구사항6 : X (사용방법 모르겠음)
        - 요구사항7 : O
    */

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            logger.debug("request line : {}", line);
            if (line == null) {
                return;
            }

            String[] tokens = line.split(" ");
            RequestLine requestLine = new RequestLine(tokens[0], tokens[1]);

            int contentLength = 0;
            boolean isLogined = false;
            while (!line.equals("")) {
                line = br.readLine();
                logger.debug("header : {}", line);

                String header = line.split(":")[0];
                if (header.equals("Content-Length")) {
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
                if (header.equals("Cookie")) {
                    logger.debug("header : {}", line);
                    String cookie = line.split(":")[1].trim();
                    String logined = cookie.split("=")[1];
                    if (logined.equals("true")) {
                        isLogined = true;
                    }
                }
            }

            String path = requestLine.getPath();
            DataOutputStream dos = new DataOutputStream(out);
            String requestBody;
            byte[] body = null;
            if (path.endsWith(".html")) {
                responseResource(dos, path);
            } else if(path.equals("/user/create")) {
                if (contentLength != 0) {
                    requestBody = IOUtils.readData(br, contentLength);
                    Map<String, String> parameters = makeParameters(requestBody);
                    User user = new User(parameters.get("userId"), parameters.get("password"),
                            parameters.get("name"), parameters.get("email"));
                    DataBase.addUser(user);
                }
                response302Header(dos, "/index.html");
            } else if(path.equals("/user/login")) {
                if (contentLength != 0) {
                    requestBody = IOUtils.readData(br, contentLength);
                    Map<String, String> parameters = makeParameters(requestBody);
                    User user = DataBase.findUserById(parameters.get("userId"));
                    if (user == null || !user.getPassword().equals(parameters.get("password"))) {
                        responseResource(dos, "/user/login_failed.html");
                    }
                    response302HeaderLogin(dos, "/index.html", "true");
                }
            } else if (path.equals("/user/list")) {
                if (!isLogined) {
                    responseResource(dos, "/login.html");
                }
                responseResource(dos, "/user/profile.html");
            }  else if(path.endsWith(".css")) {
                responseResourceCss(dos, path);
            }
            /*else {
                body = "Hello World".getBytes();
                response200Header(dos, body.length);
                responseBody(dos, body);
            }*/

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }



    private Map<String, String> makeParameters(String requestBody) {
        Map<String, String> parameters = new HashMap<>();
        String[] parameterUnits = requestBody.split("&");
        for (String unit : parameterUnits) {
            String[] keyAndValue = unit.split("=");
            parameters.put(keyAndValue[0], keyAndValue[1]);
        }
        return parameters;
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

    private void response200HeaderCss(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
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

    private void responseResource(DataOutputStream dos, String url) {
        byte[] body;
        try {
            body = FileIoUtils.loadFileFromClasspath("./templates" + url);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void responseResourceCss(DataOutputStream dos, String path) {
        byte[] body;
        try {
            body = FileIoUtils.loadFileFromClasspath("./static" + path);
            response200HeaderCss(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + url);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302HeaderLogin(DataOutputStream dos, String url, String logined) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Set-Cookie: logined=" + logined + "; Path=/ \r\n");
            dos.writeBytes("Location: " + url);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
