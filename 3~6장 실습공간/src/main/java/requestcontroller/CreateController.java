package requestcontroller;

import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static util.RequestHandlerUtil.savePostUser;

public class CreateController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateController.class);
    private User user;

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            user = savePostUser(httpRequest.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("user : {}", user);
        httpResponse.sendRedirect("index.html");
        log.info("/user/create 성공");
    }
}
