package soen.kgutwice.sana;

/**
 * Created by ailab on 2017-11-30.
 */

public class SubjectItem {

    String no;
    String subjectName;
    String subjectProfessor;
    String subjectcourseTime;
    String ClassYear;
    String ClassSemester;

    public void setNo(String no){this.no = no; }

    public String getNo() { return no; }

    public String getClassYear(){ return ClassYear; };

    public void setClassYear(String Year){this.ClassYear = Year;}

    public String getClassSemester(){ return ClassSemester; };

    public void setClassSemester(String Semester){this.ClassSemester = Semester;}

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

    public String getSubjectcourseTime() {
        return subjectcourseTime;
    }

    public void setSubjectcourseTime(String subjectcourseTime) {
        this.subjectcourseTime = subjectcourseTime;
    }
}
