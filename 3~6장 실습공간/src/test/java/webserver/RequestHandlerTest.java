package webserver;

import model.User;
import org.junit.jupiter.api.Test;
import util.RequestHandlerUtil;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {

    @Test
    void setup(){
        String a = "GET /index.html HTTP/1.1";
        assertThat(RequestHandlerUtil.splitRequestMessage(a)).isEqualTo("/index.html");
    }

    @Test
    void setup2() {
        String a = "/user/create?userId=dudwls0505&password=1234&name=leeyoungjin&email=dudwls0505@naver.com";
        assertThat(RequestHandlerUtil.saveUser(a)).isEqualTo(new User("dudwls0505","1234","leeyoungjin","dudwls0505@naver.com"));
    }
}