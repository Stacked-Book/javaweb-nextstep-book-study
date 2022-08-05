package requestcontroller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

import java.util.Collection;

public class ListController implements Controller {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getHeader("Cookie").contains("logined=true")) {
            Collection<User> users = DataBase.findAll();
            StringBuilder sb = new StringBuilder();

            sb.append("<table border='1'>");
            for (User user : users) {
                sb.append("<tr>");
                sb.append("<td>").append(user.getUserId()).append("</td>");
                sb.append("<td>").append(user.getName()).append("</td>");
                sb.append("<td>").append(user.getEmail()).append("</td>");
                sb.append("</tr>");
            }
            sb.append("</table>");
            httpResponse.forwardBody(sb.toString());
        }
        /*
           쿠키없음, 로그인실패
         */
        else if (httpRequest.getHeader("Cookie").contains("logined=false")) {
            httpResponse.sendRedirect("/user/login.html");
        }
    }
}
