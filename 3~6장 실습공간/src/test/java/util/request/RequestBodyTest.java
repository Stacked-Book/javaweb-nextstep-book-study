package util.request;


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
            () -> new Body("body")
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
        Body requestBody = new Body(body);

        // then
        String actual = requestBody.getBody();
        assertThat(actual).isEqualTo("body");
    }
}
