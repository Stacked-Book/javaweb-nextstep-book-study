package requestcontroller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LoginController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (isMatchIdAndPw(httpRequest.getParamsMap())) {
            httpResponse.addHeader("Set-Cookie", "logined=true");
            httpResponse.sendRedirect("/index.html");
            log.info("로그인성공");
        }
        else{
            log.info("로그인 실패");
            httpResponse.sendRedirect("/user/login_failed.html");
        }
    }

    private boolean isMatchIdAndPw(Map<String, String> stringStringMap) {
        return DataBase.findUserById(stringStringMap.get("userId")).getUserId().equals(stringStringMap.get("userId"))
                && DataBase.findUserById(stringStringMap.get("userId")).getPassword().equals(stringStringMap.get("password"));
    }
}
