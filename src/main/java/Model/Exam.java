/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author HP
 */
public class Exam {
    private int examID;
    private int classID;
    private int courseID;
    private String examName;
    private String examType; 
    private int totalQuestions;
    private int maxMarks;
    private Timestamp examDate;
    private int duration;
    private boolean allowViewScore;
    private boolean allowViewAnswers;
    private Course course;
    private ClassInfo classInfo;
    private List<ExamQuestion>  examQuestion;
    public Exam() {
    }

    public Exam(int examID, int classID, int courseID, String examName, String examType, int totalQuestions, int maxMarks, Timestamp examDate, int duration, boolean allowViewScore, boolean allowViewAnswers) {
        this.examID = examID;
        this.classID = classID;
        this.courseID = courseID;
        this.examName = examName;
        this.examType = examType;
        this.totalQuestions = totalQuestions;
        this.maxMarks = maxMarks;
        this.examDate = examDate;
        this.duration = duration;
        this.allowViewScore = allowViewScore;
        this.allowViewAnswers = allowViewAnswers;
    }

    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    public Timestamp getExamDate() {
        return examDate;
    }

    public void setExamDate(Timestamp examDate) {
        this.examDate = examDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isAllowViewScore() {
        return allowViewScore;
    }

    public void setAllowViewScore(boolean allowViewScore) {
        this.allowViewScore = allowViewScore;
    }

    public boolean isAllowViewAnswers() {
        return allowViewAnswers;
    }

    public void setAllowViewAnswers(boolean allowViewAnswers) {
        this.allowViewAnswers = allowViewAnswers;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public List<ExamQuestion> getExamQuestion() {
        return examQuestion;
    }

    public void setExamQuestion(List<ExamQuestion> examQuestion) {
        this.examQuestion = examQuestion;
    }
}
