package webserver;

import org.junit.jupiter.api.Test;
import util.RequestHandlerUtil;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {

    @Test
    void setup(){
        String a = "GET /index.html HTTP/1.1";
        assertThat(RequestHandlerUtil.parsing(a)).isEqualTo("/index.html");
    }
}