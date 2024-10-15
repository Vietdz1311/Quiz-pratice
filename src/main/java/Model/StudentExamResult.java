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
public class StudentExamResult {

    private int resultID;
    private int examID;
    private int studentID;
    private int examQuestionID;
    private String studentAnswer;
    private boolean isCorrect;
    private int marksObtained;
    private ExamQuestion examQuestion;

    public StudentExamResult() {
    }

    public StudentExamResult(int resultID, int examID, int studentID, int examQuestionID, String studentAnswer, boolean isCorrect, int marksObtained) {
        this.resultID = resultID;
        this.examID = examID;
        this.studentID = studentID;
        this.examQuestionID = examQuestionID;
        this.studentAnswer = studentAnswer;
        this.isCorrect = isCorrect;
        this.marksObtained = marksObtained;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getExamQuestionID() {
        return examQuestionID;
    }

    public void setExamQuestionID(int examQuestionID) {
        this.examQuestionID = examQuestionID;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public int getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(int marksObtained) {
        this.marksObtained = marksObtained;
    }

    public ExamQuestion getExamQuestion() {
        return examQuestion;
    }

    public void setExamQuestion(ExamQuestion examQuestions) {
        this.examQuestion = examQuestions;
    }
}
