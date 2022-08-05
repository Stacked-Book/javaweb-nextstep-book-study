package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {

    @Test
    @DisplayName("요청헤더 맵에 집어넣기 테스트")
    void headerInputTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.inputHeaders("Connection: keep-alive");
        headers.inputHeaders("Content-Length: 46");
        assertThat(headers.getHeader("Content-Length")).isEqualTo("46");
        Map<String, String> params = headers.getParameter();
        assertThat(params.size()).isEqualTo(2);
    }
}