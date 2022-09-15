package next.controller.qna.answer;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Result;

public class DeleteAnswerController extends AbstractController {
    private AnswerDao answerDao = new AnswerDao();
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long answerId = Long.parseLong(req.getParameter("answerId"));

        ModelAndView mav = jsonView();
        try {
            answerDao.delete(answerId);
            mav.addObject("result", Result.ok());
        } catch (DataAccessException e) {
            mav.addObject("result", Result.fail(e.getMessage()));
        }
        return mav;
    }
}
