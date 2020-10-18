package problem.projects;

import java.time.LocalDate;

public class App extends Project {
    private AvailableLanguages language;

    /**
     * Default constructor.
     */
    App(){}

    /**
     * Constructor with a language, a deadline and a name.
     * @param language
     * An AvailableLanguages member.
     * @param deadline
     * A LocalDate.
     * @param name
     * A String with the name.
     */
    public App(AvailableLanguages language, LocalDate deadline, String name){
        setLanguage(language);
        setDeadline(deadline);
        setName(name);
    }

    /**
     * Language getter.
     * @return
     * An AvailableLanguage, giving the language that the App can be written in.
     */
    public AvailableLanguages getLanguage() {
        return language;
    }

    /**
     * Language setter.
     * @param language
     * An AvailableLanguage, the new language.
     */
    public void setLanguage(AvailableLanguages language) {
        this.language = language;
    }

    /**
     * Gives a String representation of an App
     * @return
     * A String, containing details of the App.
     */
    public String toString(){
        String answer=new String();
        answer+="\tProject:\n";
        answer+="\t\tType: App\n";
        answer+="\t\tName: "+getName()+"\n";
        answer+="\t\tLanguage: "+getLanguage().toString()+"\n";
        return answer;
    }
}
