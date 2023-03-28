
import java.util.List;

public class Timetable {
    private int timetableId;
    private String weekend;
    private String day;

    private String firstExerciseType;
    private int firstRemainingSeats;
    private int firstPrice;

    private String secondExerciseType;
    private int secondRemainingSeats;
    private int secondPrice;


    public int getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(int timetableId) {
        this.timetableId = timetableId;
    }

    public String getWeekend() {
        return weekend;
    }

    public void setWeekend(String weekend) {
        this.weekend = weekend;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getFirstExerciseType() {
        return firstExerciseType;
    }

    public void setFirstExerciseType(String firstExerciseType) {
        this.firstExerciseType = firstExerciseType;
    }

    public int getFirstRemainingSeats() {
        return firstRemainingSeats;
    }

    public void setFirstRemainingSeats(int firstRemainingSeats) {
        this.firstRemainingSeats = firstRemainingSeats;
    }

    public int getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(int firstPrice) {
        this.firstPrice = firstPrice;
    }

    public String getSecondExerciseType() {
        return secondExerciseType;
    }

    public void setSecondExerciseType(String secondExerciseType) {
        this.secondExerciseType = secondExerciseType;
    }

    public int getSecondRemainingSeats() {
        return secondRemainingSeats;
    }

    public void setSecondRemainingSeats(int secondRemainingSeats) {
        this.secondRemainingSeats = secondRemainingSeats;
    }

    public int getSecondPrice() {
        return secondPrice;
    }

    public void setSecondPrice(int secondPrice) {
        this.secondPrice = secondPrice;
    }
}
