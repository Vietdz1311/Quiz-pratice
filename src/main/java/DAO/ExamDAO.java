package DAO;

import DBConnection.DBConnection;
import Model.AnswerQuestion;
import Model.CustomQuestionAnswer;
import Model.Exam;
import Model.ExamQuestion;
import Model.QuestionBank;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamDAO {

    private Connection conn;

    public ExamDAO() {
        try {
            conn = DBConnection.connect();
        } catch (Exception e) {
            System.out.println("Connection fail: " + e);
        }
    }

    public List<Exam> getAllExams() throws SQLException {
        List<Exam> exams = new ArrayList<>();
        String sql = "SELECT * FROM Exams";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                exams.add(mapToExam(rs));
            }
        }
        return exams;
    }

    public Exam getExamById(int examId) throws SQLException {
        String sql = "SELECT * FROM Exams WHERE ExamID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, examId);
            ExamQuestionDAO examQuestion = new ExamQuestionDAO();
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Exam exam = mapToExam(rs);
                    exam.setExamQuestion(examQuestion.getQuestionsByExamId(rs.getInt("ExamId")));
                    return exam;
                }
            }
        }
        return null;
    }

    public void insertExam(Exam exam) throws SQLException {
        String sql = "INSERT INTO Exams (ClassID, CourseID, ExamName, ExamType, TotalQuestions, MaxMarks, ExamDate, Duration, AllowViewScore, AllowViewAnswers) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, exam.getClassID());
            stmt.setInt(2, exam.getCourseID());
            stmt.setString(3, exam.getExamName());
            stmt.setString(4, exam.getExamType());
            stmt.setInt(5, exam.getTotalQuestions());
            stmt.setInt(6, exam.getMaxMarks());
            stmt.setTimestamp(7, new Timestamp(exam.getExamDate().getTime()));
            stmt.setInt(8, exam.getDuration());
            stmt.setBoolean(9, exam.isAllowViewScore());
            stmt.setBoolean(10, exam.isAllowViewAnswers());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void updateExam(Exam exam) throws SQLException {
        String sql = "UPDATE Exams SET ClassID = ?, CourseID = ?, ExamName = ?, ExamType = ?, TotalQuestions = ?, MaxMarks = ?, ExamDate = ?, Duration = ?, AllowViewScore = ?, AllowViewAnswers = ? WHERE ExamID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, exam.getClassID());
            stmt.setInt(2, exam.getCourseID());
            stmt.setString(3, exam.getExamName());
            stmt.setString(4, exam.getExamType());
            stmt.setInt(5, exam.getTotalQuestions());
            stmt.setInt(6, exam.getMaxMarks());
            stmt.setTimestamp(7, new Timestamp(exam.getExamDate().getTime()));
            stmt.setInt(8, exam.getDuration());
            stmt.setBoolean(9, exam.isAllowViewScore());
            stmt.setBoolean(10, exam.isAllowViewAnswers());
            stmt.setInt(11, exam.getExamID());
            stmt.executeUpdate();
        }
    }

    public void deleteExam(int examId) throws SQLException {
        String sql = "DELETE FROM Exams WHERE ExamID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, examId);
            stmt.executeUpdate();
        }
    }

    private Exam mapToExam(ResultSet rs) throws SQLException {
        Exam exam = new Exam();
        ClassDAO classDao = new ClassDAO();
        CourseDAO coueseDao = new CourseDAO();
        exam.setExamID(rs.getInt("ExamID"));
        exam.setClassID(rs.getInt("ClassID"));
        exam.setCourseID(rs.getInt("CourseID"));
        exam.setExamName(rs.getString("ExamName"));
        exam.setExamType(rs.getString("ExamType"));
        exam.setTotalQuestions(rs.getInt("TotalQuestions"));
        exam.setMaxMarks(rs.getInt("MaxMarks"));
        exam.setExamDate(rs.getTimestamp("ExamDate"));
        exam.setDuration(rs.getInt("Duration"));
        exam.setAllowViewScore(rs.getBoolean("AllowViewScore"));
        exam.setAllowViewAnswers(rs.getBoolean("AllowViewAnswers"));
        exam.setClassInfo(classDao.getClass(rs.getInt("ClassID")));
        exam.setCourse(coueseDao.getCourse(rs.getInt("CourseID")));
        return exam;
    }

    public boolean checkCorrectAnswer(ExamQuestion question, String[] studentAnswers) throws SQLException {
        CustomQuestionAnswerDAO customQuestionAnswerDao = new CustomQuestionAnswerDAO();
        AnswerQuestionDAO answerQuestionDao = new AnswerQuestionDAO();
        List<String> studentAnswersList = Arrays.asList(studentAnswers);

        if (question.getQuestionID() > 0) {
            List<AnswerQuestion> correctAnswers = answerQuestionDao.getAllAnswersByQuestionID(question.getQuestionID());

            List<String> correctAnswerTexts = new ArrayList<String>();
            for (AnswerQuestion ans : correctAnswers) {
                if (ans.isIsCorrect()) {
                    correctAnswerTexts.add(ans.getAnswerText());
                }
            }

            return correctAnswerTexts.containsAll(studentAnswersList) && correctAnswerTexts.size() == studentAnswers.length;

        } else {
            List<CustomQuestionAnswer> correctAnswers = customQuestionAnswerDao.getAnswersByExamQuestionID(question.getExamQuestionID());

            List<String> correctAnswerTexts = new ArrayList<String>();
            for (CustomQuestionAnswer ans : correctAnswers) {
                if (ans.isIsCorrect()) {
                    correctAnswerTexts.add(ans.getAnswerText());
                }
            }

            return correctAnswerTexts.containsAll(studentAnswersList) && correctAnswerTexts.size() == studentAnswers.length;
        }
    }

    public void saveStudentAnswer(int studentID, int examID, int questionID, String[] studentAnswers, boolean isCorrect, double marks) {
        String sql = "INSERT INTO StudentExamResults (ExamID, StudentID, ExamQuestionID, StudentAnswer, IsCorrect, MarksObtained) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, examID);
            ps.setInt(2, studentID);
            ps.setInt(3, questionID);
            ps.setString(4, String.join(",", studentAnswers));
            ps.setBoolean(5, isCorrect);
            ps.setDouble(6, marks);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveExamResult(int studentID, int examID, int totalCorrectAnswers, int totalQuestions, double totalMarksObtained) {
        String sql = "INSERT INTO ExamResults (ExamID, StudentID, TotalCorrectAnswers, TotalQuestions, TotalMarksObtained, SubmissionTime) VALUES (?, ?, ?, ?, ?, GETDATE())";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, examID);
            ps.setInt(2, studentID);
            ps.setInt(3, totalCorrectAnswers);
            ps.setInt(4, totalQuestions);
            ps.setDouble(5, totalMarksObtained);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
