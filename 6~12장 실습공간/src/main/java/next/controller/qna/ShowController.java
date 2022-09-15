package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;

public class ShowController extends AbstractController {

    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));

        ModelAndView mav = jspView("/qna/show.jsp");
        mav.addObject("question", questionDao.findById(questionId));
        mav.addObject("answers", answerDao.findAllByQuestionId(questionId));
        return mav;
    }
}
