package next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.AbstractController;
import core.mvc.Controller;
import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


public class AddAnswerController extends AbstractController {
    private static final Logger log =
            LoggerFactory.getLogger(AddAnswerController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Answer answer = new Answer(req.getParameter("writer"),
                req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        AnswerDao answerDao = new AnswerDao();
        Answer savedAnswer = answerDao.insert(answer);
        /*ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(mapper.writeValueAsString(savedAnswer));
        return null;*/
        /*req.setAttribute("answer", savedAnswer);
        return new JsonView();*/
        return jsonView().addObject("answer", savedAnswer);

    }
}
