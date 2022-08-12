package util.request;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequestUrlTest {

    private static final String PROTOCOL = "http://";
    private static final String AUTHORITY = "localhost";
    private static final String PORT = ":8080";
    private static final String PATH = "/index.html";
    private static final String QUERY = "?itme=1";
    private static final String FRAGMENT = "";
    public static final String URL = PROTOCOL + AUTHORITY + PORT + PATH + QUERY + FRAGMENT;


    @Test
    void regex_test() {
        String r = "([a-zA-z0-9]*://)([a-zA-z0-9.]*)(:[0-9]+)*(/*[a-zA-z/.]+)(\\?[a-zA-Z0-9=]+)*(#[a-zA-Z0-9]+)*";
        Pattern pattern = Pattern.compile(r);
        Matcher matcher = pattern.matcher(URL);

        assertThat(matcher.find()).isTrue();
    }

    /**
     * 요청 URL을 가져옵니다.
     */
    @Test
    void getRequestUrl() {
        assertDoesNotThrow(
            () -> Url.of(URL)
        );
    }


    /**
     * Url은 PROTOCOL 정보를 가지고 있습니다.
     */
    @Test
    void getProtocol() {
        Url url = Url.of(URL);
        assertThat(url.getProtocol()).isEqualTo(PROTOCOL);
    }


    /**
     * Url은 AUTHORITY 정보를 가지고 있습니다.
     */
    @Test
    void getAuthority() {
        Url url = Url.of(URL);
        assertThat(url.getAuthority()).isEqualTo(AUTHORITY);
    }


    /**
     * Url은 PORT 정보를 가지고 있습니다.
     */
    @Test
    void getPort() {
        Url url = Url.of(URL);
        assertThat(url.getPort()).isEqualTo(PORT);
    }


    /**
     * Url은 PATH 정보를가지고 있습니다.
     */
    @Test
    void getPath() {
        Url url = Url.of(URL);
        assertThat(url.getPath()).isEqualTo(PATH);
    }


    /**
     * Url은 QUERY 정보를 가지고 있습니다.
     */
    @Test
    void getQuery() {
        Url url = Url.of(URL);
        assertThat(url.getQuery()).isEqualTo(QUERY);
    }

    /**
     * Url은 FRAGMENT 정보를 가지고 있습니다.
     */
    @Test
    void getFragment() {
        Url url = Url.of(URL);
        assertThat(url.getFragment()).isNull();
    }

}
