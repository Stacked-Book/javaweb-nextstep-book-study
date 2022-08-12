package http;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;


class HttpResponseTest {
    private String testDr = "./src/test/resources/";

    @Test
    void responseForward() throws FileNotFoundException {
        HttpResponse httpResponse = new HttpResponseImpl(createOutputStream("Http_Forward.txt"));
        httpResponse.forward("/index.html");
    }

    @Test
    void responseRedirect() throws FileNotFoundException {
        HttpResponse httpResponse = new HttpResponseImpl(createOutputStream("Http_Redirect.txt"));
        httpResponse.sendRedirect("/index.html");
    }

    @Test
    void responseCookies() throws FileNotFoundException {
        HttpResponse httpResponse = new HttpResponseImpl(createOutputStream("Http_Cookie.txt"));
        httpResponse.addHeader("Set-Cookie", "logined=true");
        httpResponse.sendRedirect("/index.html");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(testDr + filename);
    }

}