package Controller.Exam;

import DAO.CourseDAO;
import DAO.CustomQuestionAnswerDAO;
import DAO.ExamDAO;
import DAO.ExamQuestionDAO;
import DAO.QuestionBankDAO;
import Model.Exam;
import Model.ExamQuestion;
import Model.Course;
import Model.CustomQuestionAnswer;
import Model.QuestionBank;
import Services.ExamService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(urlPatterns = {"/ExamController"})
public class ExamController extends HttpServlet {

    private ExamDAO examDAO;
    private ExamQuestionDAO examQuestionDAO;
    private CourseDAO courseDAO;

    @Override
    public void init() {
        examDAO = new ExamDAO();
        examQuestionDAO = new ExamQuestionDAO();
        courseDAO = new CourseDAO(); // Initialize CourseDAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = action != null ? action.trim() : "";
        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertExam(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateExam(request, response);
                    break;
                case "delete":
                    deleteExam(request, response);
                    break;
                case "view":
                    viewExam(request, response);
                    break;
                case "viewQuestions":
                    viewExamQuestions(request, response);
                    break;
                case "addQuestion":
                    addQuestionToExam(request, response);
                    break;
                case "deleteQuestion":
                    deleteQuestionFromExam(request, response);
                    break;
                case "addCustomQuestions":
                    this.addCustomQuestions(request, response);
                    break;
                case "addMultipleQuestions":
                    this.addQuestionToExam(request, response);
                    break;
                case "manageAnswers":
                    this.manageAnswers(request, response);
                    break;
                case "addAnswer":
                    addAnswer(request, response);
                    break;
                case "updateAnswers":
                    updateAnswers(request, response);
                    break;
                case "deleteAnswer":
                    deleteAnswer(request, response);
                    break;
                case "updateQuestion":
                    this.updateQuestion(request, response);
                    break;
                default:
                    listExams(request, response);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    private void manageAnswers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int examQuestionID = Integer.parseInt(request.getParameter("examQuestionID"));
        CustomQuestionAnswerDAO answerDAO = new CustomQuestionAnswerDAO();
        List<CustomQuestionAnswer> answers = answerDAO.getAnswersByExamQuestionID(examQuestionID);
        ExamQuestion examQuestion = examQuestionDAO.getExamQuestionById(examQuestionID);
        request.setAttribute("examQuestion", examQuestion);
        request.setAttribute("answers", answers);
        request.setAttribute("examQuestionID", examQuestionID);
        RequestDispatcher dispatcher = request.getRequestDispatcher("./exam/bank/manageAnswers.jsp");
        dispatcher.forward(request, response);
    }

    private void addCustomQuestions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int examID = Integer.parseInt(request.getParameter("examID"));
        String[] customQuestionTexts = request.getParameterValues("customQuestionTexts");
        String[] questionTypes = request.getParameterValues("questionTypes");
        ExamService examService = new ExamService();

        for (int i = 0; i < customQuestionTexts.length; i++) {
            String customQuestionText = customQuestionTexts[i];
            String questionType = questionTypes[i];
            String[] answerTexts = request.getParameterValues("answerTexts[" + i + "]");
            if (questionType.equals("Multiple Choice") && answerTexts != null) {
                boolean[] isCorrects = new boolean[answerTexts.length];
                for (int j = 0; j < answerTexts.length; j++) {
                    isCorrects[j] = request.getParameterValues("isCorrects[" + i + "][" + j + "]") != null;
                }
                examService.addCustomQuestionWithAnswers(examID, customQuestionText, questionType, answerTexts, isCorrects);
            } else if (questionType.equals("Essay")) {
                examService.addCustomQuestion(examID, customQuestionText, questionType);
            }
        }
        response.sendRedirect("ExamController?action=viewQuestions&examID=" + examID);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    private void listExams(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Exam> listExam = examDAO.getAllExams();
        request.setAttribute("listExam", listExam);
        RequestDispatcher dispatcher = request.getRequestDispatcher("./exam/bank/exam-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch courses from the database
        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("courses", courses);

        RequestDispatcher dispatcher = request.getRequestDispatcher("./exam/bank/exam-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int examID = Integer.parseInt(request.getParameter("examID"));
        Exam existingExam = examDAO.getExamById(examID);
        request.setAttribute("exam", existingExam);
        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("courses", courses);
        RequestDispatcher dispatcher = request.getRequestDispatcher("./exam/bank/exam-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertExam(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        Timestamp examDate = parseTimestamp(request.getParameter("examDate"));
        // Validate input data
        if (validateExamData(request)) {
            Exam newExam = new Exam(
                    0, // ID will be auto-generated
                    Integer.parseInt(request.getParameter("classID")),
                    Integer.parseInt(request.getParameter("courseID")),
                    request.getParameter("examName"),
                    request.getParameter("examType"),
                    Integer.parseInt(request.getParameter("totalQuestions")),
                    Integer.parseInt(request.getParameter("maxMarks")),
                    examDate,
                    Integer.parseInt(request.getParameter("duration")),
                    request.getParameter("allowViewScore") != null ? true : false,
                    request.getParameter("allowViewAnswers") != null ? true : false
            );
            examDAO.insertExam(newExam);
            response.sendRedirect("ExamController");
        } else {
            request.setAttribute("errorMessage", "Invalid input data");
            showNewForm(request, response);
        }
    }

    private void updateExam(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        Timestamp examDate = parseTimestamp(request.getParameter("examDate"));

        // Validate input data
        if (validateExamData(request)) {
            Exam exam = new Exam(
                    Integer.parseInt(request.getParameter("examID")),
                    Integer.parseInt(request.getParameter("classID")),
                    Integer.parseInt(request.getParameter("courseID")),
                    request.getParameter("examName"),
                    request.getParameter("examType"),
                    Integer.parseInt(request.getParameter("totalQuestions")),
                    Integer.parseInt(request.getParameter("maxMarks")),
                    examDate,
                    Integer.parseInt(request.getParameter("duration")),
                    request.getParameter("allowViewScore") != null ? true : false,
                    request.getParameter("allowViewAnswers") != null ? true : false
            );
            System.out.println(exam.isAllowViewAnswers() + " " + request.getParameter("allowViewScore"));
            examDAO.updateExam(exam);
            response.sendRedirect("ExamController");
        } else {
            request.setAttribute("errorMessage", "Invalid input data");
            showEditForm(request, response);
        }
    }

    private void deleteExam(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int examID = Integer.parseInt(request.getParameter("examID"));
        examDAO.deleteExam(examID);
        response.sendRedirect("ExamController");
    }

    private void addQuestionToExam(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int examID = Integer.parseInt(request.getParameter("examID"));
        String[] idStrings = request.getParameterValues("questionIDs");
        int questionID[] = new int[idStrings.length];
        QuestionBankDAO questionBankDao = new QuestionBankDAO();
        for (int i = 0; i < idStrings.length; i++) {
            questionID[i] = Integer.parseInt(idStrings[i]);
            QuestionBank bankQuestion = questionBankDao.getQuestionById(questionID[i]);
            ExamQuestion examQuestion = new ExamQuestion(
                    0,
                    examID,
                    questionID[i],
                    bankQuestion.getQuestionText(),
                    bankQuestion.getQuestionType(),
                    bankQuestion.getMarks()
            );
            examQuestionDAO.insertExamQuestion(examQuestion);
        }
        response.sendRedirect("ExamController?action=viewQuestions&examID=" + examID);
    }

    private boolean validateExamQuestionData(String customQuestionText, String questionType, int marksAllocated) {
        return customQuestionText != null && !customQuestionText.trim().isEmpty()
                && questionType != null && !questionType.trim().isEmpty()
                && marksAllocated > 0;
    }

    private void deleteQuestionFromExam(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int examQuestionID = Integer.parseInt(request.getParameter("examQuestionID"));
        examQuestionDAO.deleteExamQuestion(examQuestionID);
        response.sendRedirect("ExamController?action=viewQuestions&examID=" + request.getParameter("examID"));
    }

    private void viewExam(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int examID = Integer.parseInt(request.getParameter("examID"));
        Exam exam = examDAO.getExamById(examID);
        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("courses", courses);
        request.setAttribute("exam", exam);
        RequestDispatcher dispatcher = request.getRequestDispatcher("./exam/bank/viewExam.jsp");
        dispatcher.forward(request, response);
    }

    private void viewExamQuestions(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int examID = Integer.parseInt(request.getParameter("examID"));
        Exam exam = examDAO.getExamById(examID);
        List<ExamQuestion> questions = examQuestionDAO.getQuestionsByExamId(examID);
        QuestionBankDAO questionBank = new QuestionBankDAO();
        List<QuestionBank> questionbanks = questionBank.getAllQuestionsByCouerse(exam.getCourseID());
        request.setAttribute("questions", questions);
        request.setAttribute("allQuestions", questionbanks);
        request.setAttribute("examID", examID);
        request.setAttribute("exam", exam);
        RequestDispatcher dispatcher = request.getRequestDispatcher("./exam/bank/viewExamQuestions.jsp");
        dispatcher.forward(request, response);
    }

    private Timestamp parseTimestamp(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        java.util.Date utilDate = null;
        try {
            utilDate = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Timestamp(utilDate.getTime());
    }

    private boolean validateExamData(HttpServletRequest request) {
        try {
            Integer.parseInt(request.getParameter("classID"));
            Integer.parseInt(request.getParameter("courseID"));
            Integer.parseInt(request.getParameter("totalQuestions"));
            Integer.parseInt(request.getParameter("maxMarks"));
            Integer.parseInt(request.getParameter("duration"));
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e);
            return false;
        }

        String examName = request.getParameter("examName");
        String examType = request.getParameter("examType");

        return examName != null && !examName.trim().isEmpty()
                && examType != null && !examType.trim().isEmpty();
    }

    private boolean validateExamQuestionData(HttpServletRequest request) {
        // Perform necessary validation checks for exam question data
        String customQuestionText = request.getParameter("customQuestionText");
        String questionType = request.getParameter("questionType");

        try {
            Integer.parseInt(request.getParameter("marksAllocated"));
        } catch (NumberFormatException e) {
            return false; // Invalid number format
        }

        return customQuestionText != null && !customQuestionText.trim().isEmpty()
                && questionType != null && !questionType.trim().isEmpty();
    }

    private void addAnswer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int examQuestionID = Integer.parseInt(request.getParameter("examQuestionID"));
        String newAnswerText = request.getParameter("newAnswerText");
        boolean isCorrect = request.getParameter("isCorrect") != null;

        CustomQuestionAnswer newAnswer = new CustomQuestionAnswer();
        newAnswer.setExamQuestionID(examQuestionID);
        newAnswer.setAnswerText(newAnswerText);
        newAnswer.setIsCorrect(isCorrect);

        CustomQuestionAnswerDAO answerDAO = new CustomQuestionAnswerDAO();
        answerDAO.insertAnswer(newAnswer);

        response.sendRedirect("ExamController?action=manageAnswers&examQuestionID=" + examQuestionID);
    }

    private void updateAnswers(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int examQuestionID = Integer.parseInt(request.getParameter("examQuestionID"));
        String[] answerTexts = request.getParameterValues("answerTexts");

        CustomQuestionAnswerDAO answerDAO = new CustomQuestionAnswerDAO();
        List<CustomQuestionAnswer> answers = answerDAO.getAnswersByExamQuestionID(examQuestionID);

        for (int i = 0; i < answers.size(); i++) {
            CustomQuestionAnswer answer = answers.get(i);
            answer.setAnswerText(answerTexts[i]);
            answer.setIsCorrect(request.getParameter("isCorrects[" + i + "]") != null);
            answerDAO.updateAnswer(answer);
        }

        response.sendRedirect("ExamController?action=manageAnswers&examQuestionID=" + examQuestionID);
    }

    private void deleteAnswer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int answerID = Integer.parseInt(request.getParameter("deleteAnswer"));
        int examQuestionID = Integer.parseInt(request.getParameter("examQuestionID"));

        CustomQuestionAnswerDAO answerDAO = new CustomQuestionAnswerDAO();
        answerDAO.deleteAnswer(answerID);

        response.sendRedirect("ExamController?action=manageAnswers&examQuestionID=" + examQuestionID);
    }

    private void updateQuestion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int examQuestionID = Integer.parseInt(request.getParameter("examQuestionID"));
        String questionText = request.getParameter("questionText");

        ExamQuestion question = examQuestionDAO.getExamQuestionById(examQuestionID);

        if (question != null) {
            question.setCustomQuestionText(questionText);
            examQuestionDAO.updateQuestion(question);
        }
        response.sendRedirect("ExamController?action=manageAnswers&examQuestionID=" + examQuestionID);
    }

}
