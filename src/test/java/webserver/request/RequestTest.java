package webserver.request;

import jdk.nashorn.internal.ir.annotations.Ignore;
import webserver.Request;
import webserver.request.HttpRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Ignore
public class RequestTest {

    public static Request requestOfCreateUser() throws IOException {
        String pathName = "./src/test/resources/Request_CreateUser.txt";
        InputStream in = new FileInputStream(new File(pathName));
        return HttpRequest.newInstance(in);
    }

    public static Request requestOfLogin() throws IOException {
        String pathName = "./src/test/resources/Request_Login.txt";
        InputStream in = new FileInputStream(new File(pathName));
        return HttpRequest.newInstance(in);
    }

    public static Request requestOfList() throws IOException {
        String pathName = "./src/test/resources/Request_List.txt";
        InputStream in = new FileInputStream(new File(pathName));
        return HttpRequest.newInstance(in);
    }

    public static Request requestOfList_notLogin() throws IOException {
        String pathName = "./src/test/resources/Request_List_Fail.txt";
        InputStream in = new FileInputStream(new File(pathName));
        return HttpRequest.newInstance(in);
    }
}