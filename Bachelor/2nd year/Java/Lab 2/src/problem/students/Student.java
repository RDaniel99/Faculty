package problem.students;

import problem.projects.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Class describing a Student, with all of his properties.
 */
public class Student {
    private String name;
    private int yearOfStudy;
    private List<Project> preferences;

    /**
     * Default constructor.
     */
    public Student(){}

    /**
     * Constructor for a given name and a year of study.
     * @param name
     * A String, the name of the student.
     * @param yearOfStudy
     * A int, the year of study.
     */
    public Student(String name, int yearOfStudy){
        this.name=name;
        this.yearOfStudy=yearOfStudy;
        this.preferences=new ArrayList<>();
    }

    /**
     * Name getter.
     * @return
     * A String, the name of the Student.
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter.
     * @param name
     * A String representing the new name of the student.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for year of study.
     * @return
     * An int, the year of study of the Student.
     */
    public int getYearOfStudy() {
        return yearOfStudy;
    }

    /**
     * Year of study setter.
     * @param yearOfStudy
     * An int, the year of study that needs to be set
     */
    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    /**
     * Gives the list of the Projects preferred by the Student.
     * @return
     * A List of Projects, containing each preference of the Student.
     */
    public List<Project> getPreferences() {
        return preferences;
    }

    /**
     * Sets the preferences of the Student.
     * @param preferences
     * A variable number of Projects.
     */
    public void setPreferences(Project... preferences) {
        for (Project project: preferences){
            if (!this.preferences.contains(project)){
                this.preferences.add(project);
            }
        }
    }

    /**
     * Overridden method from Object class to check equality with another Student.
     * @param student
     * The Student that I want to compare the current Student.
     * @return
     * True, if the two Students are equal. False, otherwise.
     */
    public boolean equals(Student student){
        return this.getName().equals(student.getName());
    }

    /**
     * Gives a String representation of a Student.
     * @return
     * A String, containing details of a Student formatted.
     */
    public String toString(){
        String answer=new String();
        answer+="Name of the student: "+getName()+'\n';
        answer+="Year of study: "+getYearOfStudy()+'\n';
        answer+="Projects preferred: \n";
        for (Project project:getPreferences()){
            answer+=project.toString();
        }
        return answer;
    }
}
