package next.web;

import core.db.DataBase;
import next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class UpdateUserFormServletTest {

    UpdateUserFormServlet servlet = new UpdateUserFormServlet();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    @BeforeEach
    void setup() {
        User user = new User("dudwls0505", "1234", "이영진", "dudwls0505@naver.com");
        DataBase.addUser(user);
    }

    @Test
    @DisplayName("GET: 유저 수정준비 정상처리 (성공)")
    void doGetUpdateUserFormServletTest() throws ServletException, IOException {
        //given
        request.setPathInfo("/dudwls0505");

        //when
        servlet.execute(request, response);

        //then
        assertThat(request.getAttribute("user")).isSameAs(DataBase.findUserById("dudwls0505"));
    }

    @Test
    @DisplayName("GET: 존재하지않는 유저를 수정할때 redirect한다 (실패)")
    void doGetUpdateUserFormServletTest2() {
        //given
        request.setPathInfo("/ddwls0505");

        //then
        assertDoesNotThrow(() -> {
            servlet.execute(request, response);
        });
        assertThat(response.getRedirectedUrl()).isEqualTo("/user/create");
    }

    @Test
    @DisplayName("POST: 유저 수정처리 반영 (성공)")
    void doPostUpdateUserFormServletTest() throws IOException, ServletException {
        //given
        request.setParameter("userId", "dudwls0505");
        request.setParameter("password", "5678");
        request.setParameter("name", "이영진");
        request.setParameter("email", "dudwls0505@naver.com");

        //when
        servlet.execute(request, response);

        //then
        assertEquals(DataBase.findUserById(request.getParameter("userId")), new User("dudwls0505", "5678", "이영진", "dudwls0505@naver.com"));
        assertThat(response.getRedirectedUrl()).isEqualTo("/user/list");
    }

    @Test
    @DisplayName("POST: 유저 수정처리 반영실패 (실패)")
    void doPostUpdateUserFormServletTest2() {
        //given
        request.setParameter("userId", "");

        //then
        assertThrows(NoSuchElementException.class, () ->{
            servlet.execute(request,response);
        });
    }
}