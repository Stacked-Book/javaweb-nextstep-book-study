package util;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {

    @Test
    @DisplayName("HTTP요청라인에 쿼리스트링이 포함되어있지않은경우")
    void requestMethodPathTest() {
        RequestLine line = new RequestLine("GET /index.html HTTP/1.1");
        assertThat("GET").isEqualTo(line.getMethod());
        assertThat("/index.html").isEqualTo(line.getPath());
        line = new RequestLine("POST /index.html HTTP/1.1");
        assertThat("POST").isEqualTo(line.getMethod());
        assertThat("/index.html").isEqualTo(line.getPath());
    }

    @Test
    @DisplayName("HTTP요청라인에 쿼리스트링이 포함되어있는경우")
    void test2() {
        RequestLine line = new RequestLine("GET /user/create?userId=javajigi&password=password&name=Jaesung HTTP/1.1");
        assertThat("/user/create").isEqualTo(line.getPath());
        assertThat("GET").isEqualTo(line.getMethod());
        Map<String, String> params = line.getParameter();
        assertThat(params.size()).isEqualTo(3);
    }
}