package problem.projects;
import java.time.LocalDate;

/**
 * An abstract class that describes a Project in general, with some common properties and methods for an App and an Essay.
 */
public abstract class Project {
    private LocalDate deadline;
    private String name;

    /**
     * Default constructor, needed for the classes that inherit Project.
     */
    Project(){}

    /**
     * Deadline getter.
     * @return
     * A LocalDate, describing the deadline for the Project.
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * Deadline setter
     * @param deadline
     * A LocalDate describing the date that the Project should be submitted.
     */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /**
     * Name getter.
     * @return
     * A String, the Project's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter
     * @param name
     * A String, the new name of the Project.
     */
    public void setName(String name) {
        this.name = name;
    }
}
