/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author HP
 */
import DBConnection.DBConnection;
import Model.QuestionBank;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionBankDAO {

    private Connection connection;

    public QuestionBankDAO() {
        try {
            connection = DBConnection.connect();
        } catch (Exception e) {
            System.out.println("Connection fail: " + e);
        }
    }

    public int addQuestion(QuestionBank question) throws SQLException {
        String sql = "INSERT INTO QuestionBank (CourseID, QuestionText, QuestionType, Difficulty, Marks, TeacherID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, question.getCourseID());
            ps.setString(2, question.getQuestionText());
            ps.setString(3, question.getQuestionType());
            ps.setInt(4, question.getDifficulty());
            ps.setInt(5, question.getMarks());
            ps.setInt(6, question.getTeacherId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); 
                    } else {
                        throw new SQLException("Creating question failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating question failed, no rows affected.");
            }
        }
    }

    public List<QuestionBank> getAllQuestions() throws SQLException {
        List<QuestionBank> questions = new ArrayList<>();
        String sql = "SELECT * FROM QuestionBank";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        CourseDAO courseDao = new CourseDAO();
        TeacherDAO teacherDao = new TeacherDAO();
        while (rs.next()) {
            QuestionBank question = new QuestionBank();
            question.setQuestionID(rs.getInt("QuestionID"));
            question.setCourseID(rs.getInt("CourseID"));
            question.setQuestionText(rs.getString("QuestionText"));
            question.setQuestionType(rs.getString("QuestionType"));
            question.setDifficulty(rs.getInt("Difficulty"));
            question.setMarks(rs.getInt("Marks"));
            question.setTeacherId(rs.getInt("TeacherID"));
            question.setCourse(courseDao.getCourse(rs.getInt("CourseID")));
            question.setTeacher(teacherDao.getTeacher(rs.getInt("TeacherID")));
            questions.add(question);
        }
        return questions;
    }

    public List<QuestionBank> getAllQuestionsByCouerse(int courseId) throws SQLException {
        List<QuestionBank> questions = new ArrayList<>();
        String sql = "SELECT * FROM QuestionBank  where CourseID=?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, courseId);
        ResultSet rs = st.executeQuery();
        CourseDAO courseDao = new CourseDAO();
        TeacherDAO teacherDao = new TeacherDAO();
        while (rs.next()) {
            QuestionBank question = new QuestionBank();
            question.setQuestionID(rs.getInt("QuestionID"));
            question.setCourseID(rs.getInt("CourseID"));
            question.setQuestionText(rs.getString("QuestionText"));
            question.setQuestionType(rs.getString("QuestionType"));
            question.setDifficulty(rs.getInt("Difficulty"));
            question.setMarks(rs.getInt("Marks"));
            question.setTeacherId(rs.getInt("TeacherID"));
            question.setCourse(courseDao.getCourse(rs.getInt("CourseID")));
            question.setTeacher(teacherDao.getTeacher(rs.getInt("TeacherID")));
            questions.add(question);
        }
        return questions;
    }

    public QuestionBank getQuestionById(int questionId) throws SQLException {
        String sql = "SELECT * FROM QuestionBank WHERE QuestionID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, questionId);
        ResultSet rs = ps.executeQuery();
        CourseDAO courseDao = new CourseDAO();
        TeacherDAO teacherDao = new TeacherDAO();
        AnswerQuestionDAO ansQuestionDao = new AnswerQuestionDAO();
        if (rs.next()) {
            QuestionBank question = new QuestionBank();
            question.setQuestionID(rs.getInt("QuestionID"));
            question.setCourseID(rs.getInt("CourseID"));
            question.setQuestionText(rs.getString("QuestionText"));
            question.setQuestionType(rs.getString("QuestionType"));
            question.setDifficulty(rs.getInt("Difficulty"));
            question.setMarks(rs.getInt("Marks"));
            question.setTeacherId(rs.getInt("TeacherID"));
            question.setCourse(courseDao.getCourse(rs.getInt("CourseID")));
            question.setTeacher(teacherDao.getTeacher(rs.getInt("TeacherID")));
            question.setAnwsers(ansQuestionDao.getAllAnswersByQuestionID(questionId));
            return question;
        }
        return null;
    }

    public void updateQuestion(QuestionBank question) throws SQLException {
        String sql = "UPDATE QuestionBank SET CourseID = ?, QuestionText = ?, QuestionType = ?, Difficulty = ?, Marks = ? WHERE QuestionID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, question.getCourseID());
        ps.setString(2, question.getQuestionText());
        ps.setString(3, question.getQuestionType());
        ps.setInt(4, question.getDifficulty());
        ps.setInt(5, question.getMarks());
        ps.setInt(6, question.getQuestionID());
        ps.executeUpdate();
    }

    public void deleteQuestion(int questionId) throws SQLException {
        String sql = "DELETE FROM QuestionBank WHERE QuestionID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, questionId);
        ps.executeUpdate();
    }
}
