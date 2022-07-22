package webserver;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpRequestUtils;
import util.RequestHandlerUtil;

import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {

    @Test
    @DisplayName("요청메시지의 url만 자르기")
    void splitRequestMessageTest(){
        String a = "GET /index.html HTTP/1.1";
        assertThat(RequestHandlerUtil.splitRequestMessage(a)).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("GET방식으로 회원가입")
    void saveGetUser() {
        String a = "/user/create?userId=dudwls0505&password=1234&name=leeyoungjin&email=dudwls0505@naver.com";
        assertThat(RequestHandlerUtil.saveGetUser(a)).isEqualTo(new User("dudwls0505","1234","leeyoungjin","dudwls0505@naver.com"));
    }

    @Test
    @DisplayName("POST방식으로 회원가입")
    void savePostUser() {
        String header = "/user/create";
        String body = "userId=dudwls0505&password=1234&name=leeyoungjin&email=dudwls0505@naver.com";
        assertThat(RequestHandlerUtil.savePostUser(body)).isEqualTo(new User("dudwls0505", "1234", "leeyoungjin", "dudwls0505@naver.com"));

    }
}