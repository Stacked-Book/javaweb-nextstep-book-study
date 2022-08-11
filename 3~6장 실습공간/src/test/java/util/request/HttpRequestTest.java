package util.request;

import org.junit.jupiter.api.Test;
import util.HttpRequestImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HttpRequestTest {
    private static final String testDirectory = "./src/test/resources/";
    private static final String URL_CREATE = "/user/create";
    private static final String USER_ID = "geonc123";
    private static final String PASSWORD = "1234";
    private static final String NAME = "this";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String CONNECTION = "keep-alive";

    @Test
    public void request_GET() throws IOException {
        InputStream in = Files.newInputStream(new File(testDirectory + "Http_GET.txt").toPath());
        HttpRequestImpl request = new HttpRequestImpl(in);

        assertEquals(GET, request.getMethod());
        assertEquals(URL_CREATE, request.getPath());
        assertEquals(CONNECTION, request.getHeader("Connection"));
        assertEquals(USER_ID, request.getParameter("userId"));
        assertEquals(NAME, request.getParameter("name"));
        assertEquals(PASSWORD, request.getParameter("password"));
    }


    @Test
    public void request_POST() throws IOException {
        InputStream in = Files.newInputStream(new File(testDirectory + "Http_POST.txt").toPath());
        HttpRequestImpl request = new HttpRequestImpl(in);

        assertEquals(POST, request.getMethod());
        assertEquals(URL_CREATE, request.getPath());
        assertEquals(CONNECTION, request.getHeader("Connection"));
        assertEquals(USER_ID, request.getParameter("userId"));
        assertEquals(NAME, request.getParameter("name"));
        assertEquals(PASSWORD, request.getParameter("password"));
    }
}
