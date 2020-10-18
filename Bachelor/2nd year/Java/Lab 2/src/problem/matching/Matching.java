package problem.matching;

import problem.Problem;
import problem.projects.Project;
import problem.students.Student;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Contains the list of students and the list of the projects assigned to them, accordingly to their preferences
 */
public class Matching {
    private List<Project> projects;
    private List<Student> students;

    /**
     * Default contructor. Initialize the two Lists.
     */
    public Matching(){
        projects=new ArrayList<>();
        students=new ArrayList<>();
    }

    /**
     * Assign in a naive way projects to students, giving to each student the first project unassigned.
     * @param problem
     * Valid instance of a Problem.
     */
    public void makeMatchingNaive(Problem problem){
        for (Student student:problem.getStudents()){
            for (Project project:student.getPreferences()){
                if (!projects.contains(project)){
                    students.add(student);
                    projects.add(project);
                    break;
                }
            }
        }
    }

    /**
     * Assign optimally projects to students, performing a maximum matching algorithm on a graph constructed
     * with the students and projects lists from the Problem.
     * @param problem
     * Valid instance of a Problem.
     */
    public void makeMatchingOptimal(Problem problem){
        Graph g=new Graph();
        Map<String,String> matching=g.hopcroftKarp(problem);
        for (Student student:problem.getStudents()){
            if (matching.containsKey(student.getName())){
                students.add(student);
                for (Project project:problem.getProjects()){
                    if (matching.get(student.getName()).equals(project.getName())){
                        projects.add(project);
                    }
                }
            }
        }
    }

    /**
     * Verifies Hall's Theorem using the dedicated method from Graph.
     * @param problem
     * Valid instance of a Problem.
     * @return
     * return true if Hall's Theorem is verified. False, otherwise.
     */
    public boolean verifyHallTheorem(Problem problem){
        Graph g = new Graph();
        return g.hallTheorem(problem);
    }

    /**
     * Overloaded function from Object class, that gives a proper String representation of Matching.
     * @return
     * String containing each matching on a separate line.
     */
    public String toString(){
        String answer=new String();
        for (int i=0;i<students.size();i++){
            answer+="Student " + students.get(i).getName() + " is assigned with the project " + projects.get(i).getName() + '\n';
        }
        return answer;
    }
}
