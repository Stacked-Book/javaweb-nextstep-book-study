package http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequestHeaderTest {
    private static final String KEY = "Content-Type";
    private static final String VALUE = "application/x-www-form-urlencoded";

    private static final Map<String, String> params = new HashMap<>();

    @BeforeEach
    void setUp() {
        params.put(KEY, VALUE);
    }

    @Test
    void getRequestHeader() {
        assertDoesNotThrow(
            () -> new RequestHeaders(params)
        );
    }

    @Test
    void RequestHeader_getHeaders() {
        RequestHeaders header = new RequestHeaders(params);

        assertThat(header.getHeaders()).hasSize(1);
    }

    @Test
    void RequestHeader_contains() {
        RequestHeaders header = new RequestHeaders(params);

        assertThat(header.contains(KEY)).isTrue();
    }
}
