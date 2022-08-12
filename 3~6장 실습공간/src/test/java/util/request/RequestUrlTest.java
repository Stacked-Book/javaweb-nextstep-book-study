package util.request;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequestUrlTest {

    private static final String PATH = "/index.html";
    private static final String QUERY = "?itme=1";
    private static final String FRAGMENT = "";
    public static final String URL = PATH + QUERY + FRAGMENT;
    private static final String URL2 = "http://localhost:8080/user/create?userId=geonc123&password=1234&name=this#1234";

    @Test
    void regex_test() {
        String r = "^([a-zA-Z0-9]*://)?([a-zA-z0-9.]+)?(:[0-9]+)?(/*[a-zA-z/]+)*(\\?[a-zA-Z0-9=&]+)?(#[a-zA-Z0-9]+)*$";
        Pattern pattern = Pattern.compile(r);
        Matcher matcher = pattern.matcher(URL2);

        assertThat(matcher.find()).isTrue();
        for (int i = 0; i < matcher.groupCount()+1; i++) {
            System.out.println(i + " " + matcher.group(i));
        }
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

}
