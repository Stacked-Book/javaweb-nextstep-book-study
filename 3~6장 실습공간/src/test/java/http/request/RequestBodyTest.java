package http.request;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RequestBodyTest {

    /**
     * 요청 body 정보를 가집니다.
     */
    @Test
    public void getRequestBody() {
        assertDoesNotThrow(
            () -> new RequestBody("body")
        );
    }

    /**
     * body 정보를 반홥합니다.
     */
    @Test
    public void RequestBody_getBody() {
        // given
        String body = "body";

        // when
        RequestBody requestBody = new RequestBody(body);

        // then
        String actual = requestBody.getBody();
        assertThat(actual).isEqualTo("body");
    }
}
