package soen.kgutwice.sana;

/**
 * Created by dusta on 2017-11-19.
 */

public class Subject {

    private String subjectName;
    private String subjectProfessor;
    private String lectureDayOfTheWeek;
    private int startTime;
    private int endTime;
    private int takeClassYear;
    private int takeClassSemester;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectProfessor() {
        return subjectProfessor;
    }

    public void setSubjectProfessor(String subjectProfessor) {
        this.subjectProfessor = subjectProfessor;
    }

    public String getLectureDayOfTheWeek() {
        return lectureDayOfTheWeek;
    }

    public void setLectureDayOfTheWeek(String lectureDayOfTheWeek) {
        this.lectureDayOfTheWeek = lectureDayOfTheWeek;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getTakeClassYear() {
        return takeClassYear;
    }

    public void setTakeClassYear(int takeClassYear) {
        this.takeClassYear = takeClassYear;
    }

    public int getTakeClassSemester() {
        return takeClassSemester;
    }

    public void setTakeClassSemester(int takeClassSemester) {
        this.takeClassSemester = takeClassSemester;
    }
}
