package interfaces;

import utils.OpeningHours;

import java.time.Duration;
import java.time.LocalTime;

public interface Visitable {
    OpeningHours getOpeningHours();
    void setOpeningHours(OpeningHours openingHours);
    default void setOpeningHours(){
        setOpeningHours(new OpeningHours(LocalTime.of(9,30),LocalTime.of(20,0)));
    }

    LocalTime getOpeningHour();

    static Duration getVisitingDuration(OpeningHours openingHours){
        return Duration.between(openingHours.getOpeningHour(), openingHours.getClosingHour());
    }
}
