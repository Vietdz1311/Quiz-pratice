/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Model;

import java.util.List;

/**
 *
 * @author HP
 */
public class ExamQuestion {
    private int examQuestionID;
    private int examID;
    private int questionID; 
    private String customQuestionText;
    private String questionType; 
    private int marksAllocated;
    private QuestionBank questionBank;
    private List<CustomQuestionAnswer> customQuestionAnswers;

    public ExamQuestion() {
    }

    public ExamQuestion(int examQuestionID, int examID, int questionID, String customQuestionText, String questionType, int marksAllocated) {
        this.examQuestionID = examQuestionID;
        this.examID = examID;
        this.questionID = questionID;
        this.customQuestionText = customQuestionText;
        this.questionType = questionType;
        this.marksAllocated = marksAllocated;
    }

    public int getExamQuestionID() {
        return examQuestionID;
    }

    public void setExamQuestionID(int examQuestionID) {
        this.examQuestionID = examQuestionID;
    }

    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getCustomQuestionText() {
        return customQuestionText;
    }

    public void setCustomQuestionText(String customQuestionText) {
        this.customQuestionText = customQuestionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public int getMarksAllocated() {
        return marksAllocated;
    }

    public void setMarksAllocated(int marksAllocated) {
        this.marksAllocated = marksAllocated;
    }

    public QuestionBank getQuestionBank() {
        return questionBank;
    }

    public void setQuestionBank(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }

    public List<CustomQuestionAnswer> getCustomQuestionAnswers() {
        return customQuestionAnswers;
    }

    public void setCustomQuestionAnswers(List<CustomQuestionAnswer> customQuestionAnswers) {
        this.customQuestionAnswers = customQuestionAnswers;
    }
    
}
