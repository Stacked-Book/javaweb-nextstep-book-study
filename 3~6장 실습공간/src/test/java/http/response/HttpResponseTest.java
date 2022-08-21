package http.response;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @Test
    void getHttpResponseImpl() {

        // given
        String responseLine = "HTTP/1.1 200 OK";

        Map<String, String> headers = new HashMap<>();
        headers.put("Server", "Apache");
        headers.put("Content-Length", "38");
        headers.put("Content-Type", "text/html; charset=utf-8");

        String body = "<!doctype html>\n<html lang=\"ko\">\n</html>";

        // when
        HttpResponse response = new HttpResponseImpl.Builder()
            .responseLine(responseLine)
            .headers(headers)
            .body(body.getBytes())
            .build();

        assertThat(response.getResponseLine()).isEqualTo(responseLine);
        assertThat(response.getHeaders()).isEqualTo(headers);
        assertThat(response.getBody()).isEqualTo(body);
    }
}
