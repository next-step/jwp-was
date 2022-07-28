package service;

import model.HandlerInvokeResult;
import model.HttpMessage;
import model.RequestLine;
import model.UrlPath;
import utils.FileIoUtils;
import utils.HandlerAdapter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RequestService {

    private static final String BODY_SEPARATOR = "";

    public static List<String> getHttpMessageData(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        List<String> data = new ArrayList<>();
        while (!line.equals(BODY_SEPARATOR)) {
            data.add(line);
            line = bufferedReader.readLine();
        }
        return data;
    }

    public static byte[] getBody(HttpMessage httpMessage) throws IOException, URISyntaxException, InvocationTargetException, IllegalAccessException {
        RequestLine requestLine = httpMessage.getRequestLine();

        if (isRequestForFileResource(requestLine)) {
            UrlPath urlPath = requestLine.getUrlPath();
            return FileIoUtils.loadFileFromClasspath(urlPath.getPath());
        }

        HandlerInvokeResult result = HandlerAdapter.getInstance().invoke(httpMessage);
        if (result == null) {
            return null;
        }

        if (result.getClazz().equals(String.class)) {
            // TODO page templates & model return
        }

        return bodyToBytes(result.getResult());
    }

    public static boolean isRequestForFileResource(RequestLine requestLine) {
        return requestLine.getUrlPath().hasExtension();
    }

    private static byte[] bodyToBytes(Object result) throws IOException {
        if (result == null) {
            return null;
        }

        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(result);
            return boas.toByteArray();
        }
    }

}
