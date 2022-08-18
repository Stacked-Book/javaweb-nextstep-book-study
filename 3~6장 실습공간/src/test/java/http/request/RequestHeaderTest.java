package http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequestHeaderTest {
    private static final String KEY = "Content-Type";
    private static final String VALUE = "application/x-www-form-urlencoded";

    @Test
    void getRequestHeader() {
        assertDoesNotThrow(
            RequestHeaders::new
        );
    }

    @Test
    void RequestHeader_setHeader() {
        RequestHeaders header = new RequestHeaders();
        assertDoesNotThrow(
            () -> header.setHeader(KEY, VALUE)
        );
    }

    @Test
    void RequestHeader_getHeaders() {
        RequestHeaders header = new RequestHeaders();
        header.setHeader(KEY, VALUE);

        assertThat(header.getHeaders()).hasSize(1);
    }

    @Test
    void RequestHeader_getHeader() {
        RequestHeaders header = new RequestHeaders();
        header.setHeader(KEY, VALUE);

        assertThat(header.getHeader(KEY)).isEqualTo(VALUE);
    }

    @Test
    void RequestHeader_contains() {
        RequestHeaders header = new RequestHeaders();
        header.setHeader(KEY, VALUE);

        assertThat(header.contains(KEY)).isTrue();
    }
}
