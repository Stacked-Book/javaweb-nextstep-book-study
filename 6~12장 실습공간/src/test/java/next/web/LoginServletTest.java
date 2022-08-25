package next.web;

import core.db.DataBase;
import next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginServletTest {

    LoginServlet loginServlet = new LoginServlet();
    LogOutServlet logOutServlet = new LogOutServlet();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockHttpSession session = new MockHttpSession();

    @BeforeEach
    void setup() {
        User user = new User("dudwls0505", "1234", "이영진", "dudwls0505@naver.com");
        DataBase.addUser(user);
    }

    @Test
    @DisplayName(" POST: 로그인 정상 테스트")
    void doPostLoginServletTest() throws IOException {
        //given
        request.setParameter("userId", "dudwls0505");
        request.setParameter("password", "1234");

        //when
        loginServlet.doPost(request, response);

        //then
        assertThat(request.getSession().getAttribute("user")).isEqualTo(DataBase.findUserById(request.getParameter("userId")));
        assertThat(response.getRedirectedUrl()).isEqualTo("/");
    }

    @Test
    @DisplayName(" POST: 로그인 실패 테스트")
    void doPostLoginServletTest2() {
        //given
        request.setParameter("userId", "dudwls0505");
        request.setParameter("password", "5678");

        //then
        assertDoesNotThrow(() -> {
            loginServlet.doPost(request, response);
        });
        assertThat(request.getSession().getAttribute("user")).isNotEqualTo(DataBase.findUserById(request.getParameter("userId")));
        assertThat(response.getRedirectedUrl()).isEqualTo("/");
    }

    @Test
    @DisplayName("로그아웃 테스트")
    void logOutTest() throws ServletException, IOException {
        // given
        session.setAttribute("user",DataBase.findUserById("dudwls0505"));
        request.setSession(session);

        //when
        logOutServlet.doGet(request,response);

        //then
        assertTrue(session.isInvalid());
    }
}