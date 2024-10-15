/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Services;

import DAO.CustomQuestionAnswerDAO;
import DAO.ExamQuestionDAO;
import Model.CustomQuestionAnswer;
import Model.ExamQuestion;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author HP
 */
public class ExamService {
    private ExamQuestionDAO examQuestionDAO = new ExamQuestionDAO();

    public void addCustomQuestionWithAnswers(int examID, String questionText, String questionType, String[] answerTexts, boolean[] isCorrects) throws SQLException {
        ExamQuestion  exam = new ExamQuestion(0, examID, 0, questionText, questionType, 1);
        int questionID = examQuestionDAO.insertExamQuestion(exam);
        CustomQuestionAnswerDAO customQuestionAnswerDAO = new CustomQuestionAnswerDAO();
        for (int i = 0; i < answerTexts.length; i++) {
            String answerText = answerTexts[i];
            CustomQuestionAnswer cus = new CustomQuestionAnswer(0, questionID, answerText, isCorrects[i]);
            customQuestionAnswerDAO.insertCustomQuestionAnswer(cus);
        }
    }
    
    public void addCustomQuestion(int examID, String questionText, String questionType) throws SQLException {
        ExamQuestion  exam = new ExamQuestion(0, examID, 0, questionText, questionType, 1);
        examQuestionDAO.insertExamQuestion(exam);
    }
}
