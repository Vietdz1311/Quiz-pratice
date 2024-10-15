package Controller.Exam;

import DAO.AnswerQuestionDAO;
import DAO.CourseDAO; // DAO to get course list
import DAO.QuestionBankDAO;
import Model.AnswerQuestion;
import Model.QuestionBank;
import Model.Course; // Model for Course
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author HP
 */
@WebServlet(urlPatterns = {"/QuestionBankController"})
public class QuestionBankController extends HttpServlet {

    private QuestionBankDAO questionBankDAO;
    private CourseDAO courseDAO; // DAO to get course list

    public void init() {
        questionBankDAO = new QuestionBankDAO();
        courseDAO = new CourseDAO(); // Initialize CourseDAO
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = action != null ? action : "";
        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertQuestion(request, response);
                    break;
                case "delete":
                    deleteQuestion(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateQuestion(request, response);
                    break;
                default:
                    listQuestions(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    private void listQuestions(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<QuestionBank> listQuestion = questionBankDAO.getAllQuestions();
        request.setAttribute("listQuestion", listQuestion);
        RequestDispatcher dispatcher = request.getRequestDispatcher("./exam/question/question-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("courses", courses);
        RequestDispatcher dispatcher = request.getRequestDispatcher("./exam/question/question-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        QuestionBank existingQuestion = questionBankDAO.getQuestionById(id);
        List<Course> courses = courseDAO.getAllCourses();

        // Get the answers for the question
        AnswerQuestionDAO answerDAO = new AnswerQuestionDAO();
        List<AnswerQuestion> answers = answerDAO.getAnswersByQuestionId(id);

        request.setAttribute("courses", courses);
        request.setAttribute("question", existingQuestion);
        request.setAttribute("answers", answers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("./exam/question/question-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertQuestion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        String questionText = request.getParameter("questionText");
        String questionType = request.getParameter("questionType");
        int difficulty = Integer.parseInt(request.getParameter("difficulty"));
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));

        QuestionBank newQuestion = new QuestionBank(0, courseId, questionText, questionType, difficulty, 1, teacherId);
        int questionId = questionBankDAO.addQuestion(newQuestion);
        AnswerQuestionDAO answerDAO = new AnswerQuestionDAO();

        if ("Multiple Choice".equals(questionType)) {
            String[] answers = request.getParameterValues("answers");
            String[] correctAnswers = request.getParameterValues("correctAnswers");
            Set<Integer> correctAnswerIndices = new HashSet<>();
            if (correctAnswers != null) {
                for (String correctAnswer : correctAnswers) {
                    try {
                        correctAnswerIndices.add(Integer.parseInt(correctAnswer));
                    } catch (NumberFormatException e) {
                    }
                }
            }

            if (answers != null) {
                for (int i = 0; i < answers.length; i++) {
                    String answerText = answers[i];
                    if (answerText != null && !answerText.isEmpty()) {
                        boolean isCorrect = correctAnswerIndices.contains(i);
                        AnswerQuestion answerQuestion = new AnswerQuestion(0, questionId, answerText, isCorrect);
                        answerDAO.insertAnswer(answerQuestion);
                    }
                }
            }
        }
        response.sendRedirect("QuestionBankController");
    }

    private void updateQuestion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        String questionText = request.getParameter("questionText");
        String questionType = request.getParameter("questionType");
        int difficulty = Integer.parseInt(request.getParameter("difficulty"));
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));
        QuestionBank question = new QuestionBank(questionId, courseId, questionText, questionType, difficulty, 1, teacherId);
        questionBankDAO.updateQuestion(question);
        AnswerQuestionDAO answerDAO = new AnswerQuestionDAO();
        answerDAO.deleteAnswersByQuestionId(questionId);
        if ("Multiple Choice".equals(questionType)) {
            String[] answers = request.getParameterValues("answers");
            String[] correctAnswers = request.getParameterValues("correctAnswers");
            Set<Integer> correctAnswerIndices = new HashSet<>();
            if (correctAnswers != null) {
                for (String correctAnswer : correctAnswers) {
                    try {
                        correctAnswerIndices.add(Integer.parseInt(correctAnswer));
                    } catch (NumberFormatException e) {
                    }
                }
            }
            if (answers != null) {
                for (int i = 0; i < answers.length; i++) {
                    String answerText = answers[i];
                    if (answerText != null && !answerText.isEmpty()) {
                        boolean isCorrect = correctAnswerIndices.contains(i);
                        AnswerQuestion answerQuestion = new AnswerQuestion(0, questionId, answerText, isCorrect);
                        answerDAO.insertAnswer(answerQuestion);
                    }
                }
            }
        }
        response.sendRedirect("QuestionBankController");
    }

    private void deleteQuestion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int questionId = Integer.parseInt(request.getParameter("id"));
        questionBankDAO.deleteQuestion(questionId);
        response.sendRedirect("QuestionBankController");
    }
}
