package utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class OpeningHours {
    private LocalTime opening;
    private LocalTime closing;

    public OpeningHours(LocalTime opening,LocalTime closing){
        this.opening=opening;
        this.closing=closing;
    }

    public LocalTime getOpeningHour(){
        return opening;
    }

    public LocalTime getClosingHour(){
        return closing;
    }

    public void setOpeningHour(int hour, int minute){
        opening = LocalTime.of(hour, minute);
    }

    public void setClosingHour(int hour, int minute){
        closing = LocalTime.of(hour,minute);
    }
}
