package ru.cbr.edu.search;

import ru.cbr.edu.model.Absence;

public class SearchAbsence extends Absence {
    private String dateOp;
    private String startTimeOp;
    private String endTimeOp;

    public String getDateOp() {
        return dateOp;
    }

    public void setDateOp(String dateOp) {
        this.dateOp = dateOp;
    }

    public String getStartTimeOp() {
        return startTimeOp;
    }

    public void setStartTimeOp(String startTimeOp) {
        this.startTimeOp = startTimeOp;
    }

    public String getEndTimeOp() {
        return endTimeOp;
    }

    public void setEndTimeOp(String endTimeOp) {
        this.endTimeOp = endTimeOp;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "SearchAbsence{" +
                "dateOp='" + dateOp + '\'' +
                ", startTimeOp='" + startTimeOp + '\'' +
                ", endTimeOp='" + endTimeOp + '\'' +
                '}';
    }
}