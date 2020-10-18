package problem.projects;

import java.time.LocalDate;

/**
 * A class which describes a Project(that can be either Essay or App) that is of Essay type
 */
public class Essay extends Project {
    private AvailableTopics topic;

    /**
     * Default constructor
     */
    Essay(){}

    /**
     * Constructor for a topic, a deadline and a name.
     * @param topic
     * One of the AvailableTopics.
     * @param deadline
     * A LocalDate for when the essay should be submitted.
     * @param name
     * A String describing the name of the Essay.
     */
    public Essay(AvailableTopics topic, LocalDate deadline, String name){
        this.setTopic(topic);
        this.setDeadline(deadline);
        this.setName(name);
    }

    /**
     * Topic getter.
     * @return
     * An AvailableTopics, the topic assigned to the respective Essay.
     */
    public AvailableTopics getTopic() {
        return topic;
    }

    /**
     * Topic setter.
     * @param topic
     * The AvailableTopic, describing the new topic of the Essay.
     */
    public void setTopic(AvailableTopics topic) {
        this.topic = topic;
    }

    /**
     * @return
     * A String representation of an Essay.
     */
    public String toString() {
        String answer=new String();
        answer = "\tProject:\n";
        answer += "\t\tType: Essay\n";
        answer += "\t\tName: " + getName() + '\n';
        answer += "\t\tTopic: " + getTopic() + '\n';
        answer += "\t\tDeadline: " + getDeadline() + '\n';
        return answer;
    }
}
