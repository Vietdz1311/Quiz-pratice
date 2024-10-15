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
import Model.AnswerQuestion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AnswerQuestionDAO {
    private Connection conn;

    public AnswerQuestionDAO() {
        try {
            conn = DBConnection.connect();
        } catch (Exception e) {
            System.out.println("Connection failed: " + e);
        }
    }

    public List<AnswerQuestion> getAllAnswers() throws SQLException {
        List<AnswerQuestion> answers = new ArrayList<>();
        String sql = "SELECT * FROM QuestionAnswers";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                answers.add(mapToAnswer(rs));
            }
        }
        return answers;
    }
    
    public List<AnswerQuestion> getAllAnswersByQuestionID(int id) throws SQLException {
        List<AnswerQuestion> answers = new ArrayList<>();
        String sql = "SELECT * FROM QuestionAnswers where QuestionID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                answers.add(mapToAnswer(rs));
            }
        }catch(Exception e) {
            System.err.println("Error: " + e);
        }
        return answers;
    }

    public AnswerQuestion getAnswerById(int answerID) throws SQLException {
        String sql = "SELECT * FROM QuestionAnswers WHERE AnswerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, answerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapToAnswer(rs);
                }
            }
        }
        return null;
    }

    public List<AnswerQuestion> getAnswersByQuestionId(int questionID) throws SQLException {
        List<AnswerQuestion> answers = new ArrayList<>();
        String sql = "SELECT * FROM QuestionAnswers WHERE QuestionID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, questionID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    answers.add(mapToAnswer(rs));
                }
            }
        }
        return answers;
    }

    public void insertAnswer(AnswerQuestion answer) throws SQLException {
        String sql = "INSERT INTO QuestionAnswers (QuestionID, AnswerText, IsCorrect) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, answer.getQuestionID());
            stmt.setString(2, answer.getAnswerText());
            stmt.setBoolean(3, answer.isIsCorrect());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Insert error: " + e);
        }
    }

    public void updateAnswer(AnswerQuestion answer) throws SQLException {
        String sql = "UPDATE QuestionAnswers SET QuestionID = ?, AnswerText = ?, IsCorrect = ? WHERE AnswerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, answer.getQuestionID());
            stmt.setString(2, answer.getAnswerText());
            stmt.setBoolean(3, answer.isIsCorrect());
            stmt.setInt(4, answer.getAnswerID());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Update error: " + e);
        }
    }

    public void deleteAnswer(int answerID) throws SQLException {
        String sql = "DELETE FROM QuestionAnswers WHERE AnswerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, answerID);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Delete error: " + e);
        }
    }

    private AnswerQuestion mapToAnswer(ResultSet rs) throws SQLException {
        AnswerQuestion answer = new AnswerQuestion();
        answer.setAnswerID(rs.getInt("AnswerID"));
        answer.setQuestionID(rs.getInt("QuestionID"));
        answer.setAnswerText(rs.getString("AnswerText"));
        answer.setIsCorrect(rs.getBoolean("IsCorrect"));
        return answer;
    }
    
      public void deleteAnswersByQuestionId(int questionId) throws SQLException {
        String sql = "DELETE FROM QuestionAnswers WHERE QuestionID = ?";
        try (
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, questionId);
            int rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to delete answers for question ID: " + questionId, e);
        }
    }
}
