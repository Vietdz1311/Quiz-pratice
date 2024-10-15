/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Model;

/**
 *
 * @author HP
 */
public class CustomQuestionAnswer {
    private int customAnswerID;
    private int examQuestionID;
    private String answerText;
    private boolean isCorrect;

    public CustomQuestionAnswer() {
    }

    public CustomQuestionAnswer(int customAnswerID, int examQuestionID, String answerText, boolean isCorrect) {
        this.customAnswerID = customAnswerID;
        this.examQuestionID = examQuestionID;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    public int getCustomAnswerID() {
        return customAnswerID;
    }

    public void setCustomAnswerID(int customAnswerID) {
        this.customAnswerID = customAnswerID;
    }

    public int getExamQuestionID() {
        return examQuestionID;
    }

    public void setExamQuestionID(int examQuestionID) {
        this.examQuestionID = examQuestionID;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
