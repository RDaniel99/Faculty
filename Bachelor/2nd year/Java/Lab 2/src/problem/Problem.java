package problem;

import problem.projects.Project;
import problem.students.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Problem instance, with a List of Students, each Student having a List of preferred Projects.
 */
public class Problem {
    private List<Student> students;

    /**
     * Default constructor.
     */
    public Problem(){
        students=new ArrayList<>();
    }

    /**
     * Setter for the List of Students.
     * @param students
     * A variable number of Students.
     */
    public void setStudents(Student... students){
        for (Student student:students){
            if (!this.students.contains(student))
                this.students.add(student);
        }
    }

    /**
     * Getter for Students List.
     * @return
     * A List containing Student objects.
     */
    public List<Student> getStudents(){
        return students;
    }

    /**
     * Overloaded method from Object class, giving a proper String representation of a Problem instance.
     * @return
     * String containing all of the details of a Problem: each Student on a separate line.
     */
    public String toString(){
        String answer=new String();
        for (Student student:students){
            answer+=student.toString();
        }
        return answer;
    }

    /**
     * Getter for all the different Projects present in the Students choices.
     * @return
     * A List of Projects present in the choices of any Student.
     */
    public ArrayList<Project> getProjects(){
        ArrayList<Project> projectsList = new ArrayList<>();
        for (Student student:students){
            for (Project project:student.getPreferences()){
                if (!projectsList.contains(project)){
                    projectsList.add(project);
                }
            }
        }
        return projectsList;
    }

}
