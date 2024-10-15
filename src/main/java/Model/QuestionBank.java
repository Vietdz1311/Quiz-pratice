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
public class QuestionBank {

    private int questionID;
    private int courseID;
    private String questionText;
    private String questionType;
    private int difficulty;
    private int marks;
    private int teacherId;
    private Course course;
    private Teacher teacher;
    List<AnswerQuestion> anwsers; 

    public QuestionBank() {
    }

    public QuestionBank(int questionId, int courseId, String questionText, String questionType, int difficulty, int marks, int teacherId) {
        this.questionID = questionId;
        this.courseID = courseId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.difficulty = difficulty;
        this.marks = marks;
        this.teacherId = teacherId;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionId) {
        this.questionID = questionId;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseId) {
        this.courseID = courseId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<AnswerQuestion> getAnwsers() {
        return anwsers;
    }

    public void setAnwsers(List<AnswerQuestion> anwsers) {
        this.anwsers = anwsers;
    }
    
}
