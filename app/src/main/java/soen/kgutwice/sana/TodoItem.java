package soen.kgutwice.sana;

/**
 * Created by ailab on 2017-11-27.
 */

import android.graphics.drawable.Drawable;

public class TodoItem {

    String todo;
    String subject;
    String deadline;
    String actualCompletedDay;
    int ClassYear;
    int ClassSemester;
    Boolean completed; // True이면 완료된것.

    float importance;

    public int getClassYear(){ return ClassYear; }

    public void setClassYear(int Year){ this.ClassYear = Year;}

    public int getClassSemester(){ return ClassSemester; }

    public void setClassSemester(int Semester){ this.ClassSemester = Semester;}

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getActualCompletedDay() {
        return actualCompletedDay;
    }

    public void setActualCompletedDay(String actualCompletedDay) {
        this.actualCompletedDay = actualCompletedDay;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public float getImportance() {
        return importance;
    }

    public void setImportance(float importance) {
        this.importance = importance;
    }
}