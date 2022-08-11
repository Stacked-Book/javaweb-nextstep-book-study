package util.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequestHeaderTest {
    private static final String KEY = "Content-Type";
    private static final String VALUE = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE = KEY + ": " + VALUE;

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
            () -> header.setHeader(CONTENT_TYPE)
        );
    }

    @Test
    void RequestHeader_getHeaders() {
        RequestHeader header = new RequestHeader();
        header.setHeader(CONTENT_TYPE);

        assertThat(header.getHeaders()).hasSize(1);
    }

    @Test
    void RequestHeader_getHeader() {
        RequestHeader header = new RequestHeader();
        header.setHeader(CONTENT_TYPE);

        assertThat(header.getHeader(KEY)).isEqualTo(VALUE);
    }

    @Test
    void RequestHeader_contains() {
        RequestHeader header = new RequestHeader();
        header.setHeader(CONTENT_TYPE);

        assertThat(header.contains(KEY)).isTrue();
    }
}
