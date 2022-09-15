package next.controller.qna.question;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.exception.CannotDeleteException;
import next.service.QnaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteQuestionController extends AbstractController {

    private QuestionDao questionDao = QuestionDao.getInstance();
    private AnswerDao answerDao = AnswerDao.getInstance();
    private QnaService qnaService = QnaService.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        try {
            qnaService.deleteQuestion(questionId,
                    UserSessionUtils.getUserFromSession(req.getSession()));
            return jspView("redirect:/");
        } catch (CannotDeleteException e) {
            return jspView("show.jsp")
                    .addObject("question", questionDao.findById(questionId))
                    .addObject("answers", answerDao.findAllByQuestionId(questionId))
                    .addObject("errorMessage", e.getMessage());
        }
    }
}
