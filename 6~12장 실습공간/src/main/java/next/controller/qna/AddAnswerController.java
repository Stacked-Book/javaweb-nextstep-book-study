package next.controller.qna;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import next.dao.AnswerDao;
import next.model.Answer;

public class AddAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    private AnswerDao answerDao = new AnswerDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        log.debug("answer : {}", answer);

//        AnswerDao answerDao = new AnswerDao();
//        Answer savedAnswer = answerDao.insert(answer);
//        ObjectMapper mapper = new ObjectMapper();
//        resp.setContentType("application/json;charset=UTF-8");
//        PrintWriter out = resp.getWriter();
//        out.print(mapper.writeValueAsString(savedAnswer));
//        return null;

        Answer savedAnswer = answerDao.insert(answer);
        return jsonView().addObject("answer", savedAnswer);
    }
}
