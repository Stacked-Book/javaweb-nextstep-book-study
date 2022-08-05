package util;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

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
        HttpRequest request = new HttpRequest(in);

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
        HttpRequest request = new HttpRequest(in);

        assertEquals(POST, request.getMethod());
        assertEquals(URL_CREATE, request.getPath());
        assertEquals(CONNECTION, request.getHeader("Connection"));
        assertEquals(USER_ID, request.getParameter("userId"));
        assertEquals(NAME, request.getParameter("name"));
        assertEquals(PASSWORD, request.getParameter("password"));
    }
}
