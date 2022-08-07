package webserver.request;

import com.google.common.base.Charsets;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {

    private RequestLine requestLine;
    private Header header;
    private RequestBody requestBody;

    public HttpRequest(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charsets.UTF_8));

        requestLine = RequestLine.parse(br.readLine());
        header = Header.from(br);

        if (requestLine.getMethod().isPost()) {
            int readNums = Integer.parseInt(header.getHeader("Content-Length"));
            String body = IOUtils.readData(br, readNums);
            System.out.println(body);
            requestBody = new RequestBody(body);
        }
    }

    public Method getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String header) {
        return this.header.getHeader(header);
    }

    public String getParameter(String parameter) {
        Method method = requestLine.getMethod();
        if (method.isGet())
            return requestLine.getQueryString().getParameter(parameter);
        return requestBody.getParameter(parameter);
    }

    public RequestBody getQueryString() {
        return requestLine.getQueryString();
    }

    public RequestBody getBody() {
        return requestBody;
    }
}
