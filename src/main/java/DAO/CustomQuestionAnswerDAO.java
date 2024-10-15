/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBConnection.DBConnection;
import Model.CustomQuestionAnswer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class CustomQuestionAnswerDAO {

    private Connection connection;

    public CustomQuestionAnswerDAO() {
        try {
            connection = DBConnection.connect();
        } catch (Exception e) {
            System.out.println("Connection fail: " + e);
        }
    }

    public void insertCustomQuestionAnswer(CustomQuestionAnswer answer) throws SQLException {
        String sql = "INSERT INTO CustomQuestionAnswers (ExamQuestionID, AnswerText, IsCorrect) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, answer.getExamQuestionID());
            pstmt.setString(2, answer.getAnswerText());
            pstmt.setBoolean(3, answer.isIsCorrect());
            pstmt.executeUpdate();
        }
    }

    public void deleteCustomQuestionAnswersByExamQuestionID(int examQuestionID) throws SQLException {
        String sql = "DELETE FROM CustomQuestionAnswers WHERE ExamQuestionID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, examQuestionID);
            pstmt.executeUpdate();
        }
    }

    public List<CustomQuestionAnswer> getAnswersByExamQuestionID(int examQuestionID) throws SQLException {
        List<CustomQuestionAnswer> answersList = new ArrayList<>();
        String sql = "SELECT * FROM CustomQuestionAnswers WHERE ExamQuestionID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, examQuestionID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CustomQuestionAnswer answer = new CustomQuestionAnswer();
                answer.setCustomAnswerID(rs.getInt("CustomAnswerID"));
                answer.setExamQuestionID(rs.getInt("ExamQuestionID"));
                answer.setAnswerText(rs.getString("AnswerText"));
                answer.setIsCorrect(rs.getBoolean("IsCorrect"));
                answersList.add(answer);
            }
        }
        return answersList;
    }
    
    public void insertAnswer(CustomQuestionAnswer answer) throws SQLException {
        String sql = "INSERT INTO CustomQuestionAnswers (examQuestionID, answerText, isCorrect) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, answer.getExamQuestionID());
            statement.setString(2, answer.getAnswerText());
            statement.setBoolean(3, answer.isIsCorrect());
            statement.executeUpdate();
        }
    }
    
    public void updateAnswer(CustomQuestionAnswer answer) throws SQLException {
        String sql = "UPDATE CustomQuestionAnswers SET answerText = ?, isCorrect = ? WHERE CustomAnswerID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, answer.getAnswerText());
            statement.setBoolean(2, answer.isIsCorrect());
            statement.setInt(3, answer.getCustomAnswerID());
            statement.executeUpdate();
        }
    }

    // Delete an answer by answer ID
    public void deleteAnswer(int answerID) throws SQLException {
        String sql = "DELETE FROM CustomQuestionAnswers WHERE CustomAnswerID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, answerID);
            statement.executeUpdate();
        }
    }

}
