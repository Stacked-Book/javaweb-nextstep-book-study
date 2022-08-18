package http;

import response.HttpResponseImpl;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class HttpResponseTest {

    private String testDirectory = "./src/test/resources/";

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }

    @Test
    public void responseForward() throws Exception {
        HttpResponseImpl response
                = new HttpResponseImpl(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");
    }

    @Test
    public void responseRedirect() throws Exception {
        HttpResponseImpl response
                = new HttpResponseImpl(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
    }

}
