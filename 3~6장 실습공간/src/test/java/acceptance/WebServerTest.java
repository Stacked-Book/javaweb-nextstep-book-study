package acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 해당 테스트는 WebServer를 실행해야 동작합니다.
 */
public class WebServerTest {

    @Test
    public void sendIndexHtml() {
        ExtractableResponse<Response> 메인_페이지_조회 = RestAssured.given().log().all()
            .when().get("/index.html")
            .then().log().all().extract();

        assertThat(메인_페이지_조회.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }


    @Test
    public void sendFormHtml() {
        ExtractableResponse<Response> 회원가입_페이지_조회 = RestAssured.given().log().all()
            .when().get("/user/form.html")
            .then().log().all().extract();

        assertThat(회원가입_페이지_조회.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    /**
     * Given : 사용자는 회원가입할 수 있다.
     */
    @Test
    public void sendRegister() {
        // given
        ExtractableResponse<Response> 회원가입_요청 = RestAssured.given().log().all()
            .body("userId=javajigi&password=password&name=JaeSung&email=javajigi@slipp.net")
            .when().post("/user/create")
            .then().log().all().extract();

        assertThat(회원가입_요청.statusCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);
        assertThat(회원가입_요청.headers().get("Location").getValue()).isEqualTo("/index.html");

    }

    /**
     * Given : 사용자가 회원가입을 하면
     * When : 로그인할 수 있다.
     */
    @Test
    public void registerAndLogin() {
        // given
        ExtractableResponse<Response> 회원가입_요청 = RestAssured.given().log().all()
            .body("userId=geonc123&password=1234&name=this&email=rjsckdd12@gmail.com")
            .when().post("/user/create")
            .then().log().all().extract();

        assertThat(회원가입_요청.statusCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);

        // when
        ExtractableResponse<Response> 로그인_요청 = RestAssured.given().log().all()
            .body("userId=geonc123&password=1234")
            .when().post("/user/login")
            .then().log().all().extract();

        assertThat(로그인_요청.statusCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);
        assertThat(로그인_요청.headers().get("Location").getValue()).isEqualTo("/index.html");
        assertThat(로그인_요청.cookie("logined")).isEqualTo("true");

    }


    /**
     * 로그인이 실패하면 /user/login_failed.html 페이지로 이동한다.
     */
    @Test
    public void loginFailed() {
        ExtractableResponse<Response> 로그인_요청 = RestAssured.given().log().all()
            .body("userId=nouser&password=1234")
            .when().post("/user/login")
            .then().log().all().extract();

        assertThat(로그인_요청.statusCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);
        assertThat(로그인_요청.headers().get("Location").getValue()).isEqualTo("/user/login_failed.html");
        assertThat(로그인_요청.cookie("logined")).isEqualTo("false");

    }
}
