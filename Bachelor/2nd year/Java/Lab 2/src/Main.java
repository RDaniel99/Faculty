import problem.matching.Matching;
import problem.projects.*;

import java.time.LocalDate;
import java.time.Month;
import problem.students.Student;
import problem.Problem;

public class Main {

    public static void main(String[] args) {
        Student s1,s2,s3,s4;
        App a1,a2,a3;
        Essay e1,e2;
        s1=new Student("S1",2);
        s2=new Student("S2",1);
        s3=new Student("S3",3);
        s4=new Student("S4",2);
        a1=new App(AvailableLanguages.JAVA, LocalDate.of(2019, Month.JUNE, 1),"A1");
        a2=new App(AvailableLanguages.CPP, LocalDate.parse("2019-06-01"),"A2");
        a3=new App(AvailableLanguages.PYTHON,LocalDate.parse("2019-06-01"),"A3");
        e1=new Essay(AvailableTopics.ALGORITHMS,LocalDate.of(2019,Month.JULY,1),"E1");
        e2=new Essay(AvailableTopics.WEB,LocalDate.of(2019,Month.MAY,30),"E2");
        s1.setPreferences(a1,e2,e1);
        s2.setPreferences(a1,e1);
        s3.setPreferences(a2,a3,e1);
        s4.setPreferences(a1,e1);
        Problem problem=new Problem();
        problem.setStudents(s1,s2,s3,s4);
        System.out.println(problem);
        Matching match=new Matching();
        System.out.println(match.verifyHallTheorem(problem));
        match.makeMatchingOptimal(problem);
        System.out.println(match);
    }
}
