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
            RequestHeader::new
        );
    }

    @Test
    void RequestHeader_setHeader() {
        RequestHeader header = new RequestHeader();
        assertDoesNotThrow(
            () -> header.setHeader(KEY, VALUE)
        );
    }

    @Test
    void RequestHeader_getHeaders() {
        RequestHeader header = new RequestHeader();
        header.setHeader(KEY, VALUE);

        assertThat(header.getHeaders()).hasSize(1);
    }

    @Test
    void RequestHeader_getHeader() {
        RequestHeader header = new RequestHeader();
        header.setHeader(KEY, VALUE);

        assertThat(header.getHeader(KEY)).isEqualTo(VALUE);
    }

    @Test
    void RequestHeader_contains() {
        RequestHeader header = new RequestHeader();
        header.setHeader(KEY, VALUE);

        assertThat(header.contains(KEY)).isTrue();
    }
}
