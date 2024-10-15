/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBConnection.DBConnection;
import Model.ExamQuestion;
import Model.QuestionBank;
import Model.StudentExamResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExamQuestionDAO {

    private Connection conn;

    public ExamQuestionDAO() {
        try {
            conn = DBConnection.connect();
        } catch (Exception e) {
            System.out.println("Connection fail: " + e);
        }
    }

    public int insertExamQuestion(ExamQuestion examQuestion) throws SQLException {
        String sql = "INSERT INTO ExamQuestions (ExamID, QuestionID, CustomQuestionText, QuestionType, MarksAllocated) VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1; // Initialize with a default value indicating no ID generated

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, examQuestion.getExamID());
            stmt.setInt(2, examQuestion.getQuestionID());
            stmt.setString(3, examQuestion.getCustomQuestionText());
            stmt.setString(4, examQuestion.getQuestionType());
            stmt.setInt(5, examQuestion.getMarksAllocated());

            stmt.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1); // Get the generated ID
                }
            }
        }

        return generatedId;
    }

    public void updateExamQuestion(ExamQuestion examQuestion) throws SQLException {
        String sql = "UPDATE ExamQuestions SET QuestionID = ?, CustomQuestionText = ?, QuestionType = ?, MarksAllocated = ? WHERE ExamQuestionID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, examQuestion.getQuestionID());
            stmt.setString(2, examQuestion.getCustomQuestionText());
            stmt.setString(3, examQuestion.getQuestionType());
            stmt.setInt(4, examQuestion.getMarksAllocated());
            stmt.setInt(5, examQuestion.getExamQuestionID());
            stmt.executeUpdate();
        }
    }

    public void deleteExamQuestion(int examQuestionID) throws SQLException {
        String sql = "DELETE FROM ExamQuestions WHERE ExamQuestionID = ?";
        CustomQuestionAnswerDAO customDao = new CustomQuestionAnswerDAO();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, examQuestionID);
            int result = stmt.executeUpdate();
            if (result > 0) {
                customDao.deleteCustomQuestionAnswersByExamQuestionID(examQuestionID);
            }
        }
    }

    public ExamQuestion getExamQuestionById(int examQuestionID) throws SQLException {
        String sql = "SELECT * FROM ExamQuestions WHERE ExamQuestionID = ?";
        ExamQuestion examQuestion = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, examQuestionID);
            ResultSet rs = stmt.executeQuery();
            QuestionBankDAO questionBankDao = new QuestionBankDAO();
            if (rs.next()) {
                examQuestion = new ExamQuestion(
                        rs.getInt("ExamQuestionID"),
                        rs.getInt("ExamID"),
                        rs.getInt("QuestionID"),
                        rs.getString("CustomQuestionText"),
                        rs.getString("QuestionType"),
                        rs.getInt("MarksAllocated")
                );
                examQuestion.setQuestionBank(questionBankDao.getQuestionById(rs.getInt("QuestionID")));
            }
        }
        return examQuestion;
    }

    public List<ExamQuestion> getQuestionsByExamId(int examID) throws SQLException {
        String sql = "SELECT * FROM ExamQuestions WHERE ExamID = ?";
        List<ExamQuestion> questions = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, examID);
            ResultSet rs = stmt.executeQuery();
            QuestionBankDAO questionBankDao = new QuestionBankDAO();
            CustomQuestionAnswerDAO customQuestionAnswerDao = new CustomQuestionAnswerDAO();
            while (rs.next()) {
                ExamQuestion examQuestion = new ExamQuestion(
                        rs.getInt("ExamQuestionID"),
                        rs.getInt("ExamID"),
                        rs.getInt("QuestionID"),
                        rs.getString("CustomQuestionText"),
                        rs.getString("QuestionType"),
                        rs.getInt("MarksAllocated")
                );
                examQuestion.setCustomQuestionAnswers(customQuestionAnswerDao.getAnswersByExamQuestionID(rs.getInt("ExamQuestionID")));
                examQuestion.setQuestionBank(questionBankDao.getQuestionById(rs.getInt("QuestionID")));
                questions.add(examQuestion);
            }
        }
        return questions;
    }

    public void updateQuestion(ExamQuestion question) throws SQLException {
        String sql = "UPDATE ExamQuestions SET CustomQuestionText = ? WHERE examQuestionID = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, question.getCustomQuestionText());
            statement.setInt(2, question.getExamQuestionID());
            statement.executeUpdate();
        }
    }
    
    public List<StudentExamResult> getExamHistoryByStudentIdExamId(int studentID, int examID) throws SQLException {
        List<StudentExamResult> studentExamResults = new ArrayList<>();
        String sql = "Select * from StudentExamResults where studentID = ? and examID = ?";
        StudentExamResult studentResult = null;
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, studentID);
            st.setInt(2, examID);
            ResultSet rs = st.executeQuery();
            CustomQuestionAnswerDAO customQuestionAnswerDao = new CustomQuestionAnswerDAO();
            ExamQuestionDAO examQuestionDao = new ExamQuestionDAO();
            QuestionBankDAO questionBankDao = new QuestionBankDAO();
            ExamQuestion examQuestion = null;
            while(rs.next()) {
                studentResult = new StudentExamResult();
                examQuestion = examQuestionDao.getExamQuestionById(rs.getInt("ExamQuestionID"));
                studentResult.setResultID(rs.getInt("ResultID"));
                studentResult.setExamID(rs.getInt("ExamID"));
                studentResult.setStudentID(rs.getInt("StudentID"));
                studentResult.setExamQuestionID(rs.getInt("ExamQuestionID"));
                studentResult.setStudentAnswer(rs.getString("StudentAnswer"));
                studentResult.setIsCorrect(rs.getBoolean("IsCorrect"));
                studentResult.setMarksObtained(rs.getInt("MarksObtained"));
                if(examQuestion != null && examQuestion.getQuestionID() > 0)  {
                    examQuestion.setQuestionBank(questionBankDao.getQuestionById(examQuestion.getQuestionID()));
                }else  {
                    examQuestion.setCustomQuestionAnswers(customQuestionAnswerDao.getAnswersByExamQuestionID(rs.getInt("ExamQuestionID")));
                }
                studentResult.setExamQuestion(examQuestion);
                studentExamResults.add(studentResult);
            }
        }catch(Exception e) {
            System.out.println("Error: " + e);
        }
        return studentExamResults;
    }
}
