package http;

import org.junit.jupiter.api.Test;

import java.io.*;
import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private String testDr = "./src/test/resources/";

    @Test
    void request_GET() throws IOException {
        InputStream in = new FileInputStream(new File(testDr + "Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);
        assertThat(HttpMethod.GET).isEqualTo(request.getMethod());
        assertThat("/user/create").isEqualTo(request.getPath());
        assertThat("keep-alive").isEqualTo(request.getHeader("Connection"));
        assertThat("javajigi").isEqualTo(request.getParameter("userId"));
    }

    @Test
    void request_POST() throws IOException {
        InputStream in = new FileInputStream(new File(testDr + "Http_POST.txt"));
        HttpRequest request = new HttpRequest(in);
        assertThat(HttpMethod.POST).isEqualTo(request.getMethod());
        assertThat("/user/create").isEqualTo(request.getPath());
        assertThat("keep-alive").isEqualTo(request.getHeader("Connection"));
        assertThat("javajigi").isEqualTo(request.getParameter("userId"));
    }

}
