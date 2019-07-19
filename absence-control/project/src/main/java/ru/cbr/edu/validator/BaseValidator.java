package ru.cbr.edu.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.cbr.edu.model.Absence;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public abstract class BaseValidator implements Validator {
    public void checkAndParseDate(Absence absence, Errors errors, String field, String msg) {
        Optional<LocalDate> date = parseDate(absence.getDateStr(), errors, field, msg);
        if (date.isPresent()) {
            absence.setDate(date.get());
        }
    }

    public void checkAndParseTime(Absence absence, Errors errors, String field, String msg, boolean startTime) {
        Optional<LocalTime> time = parseTime(startTime ? absence.getStartTimeStr() : absence.getEndTimeStr(),
                errors, field, msg);
        if (time.isPresent()) {
            if (startTime) {
                absence.setStartTime(time.get());
            } else {
                absence.setEndTime(time.get());
            }
        }
    }

    private Optional<LocalDate> parseDate(String strField, Errors errors, String field, String msg) {
        String dateStr = strField;
        if (dateStr != null && !dateStr.trim().isEmpty()) {
            try {
                return Optional.of(LocalDate.parse(dateStr));
            } catch (DateTimeParseException ignored) {
                errors.rejectValue(field, msg);
            }
        }
        return Optional.empty();
    }

    private Optional<LocalTime> parseTime(String strField, Errors errors, String field, String msg) {
        String dateStr = strField;
        if (dateStr != null && !dateStr.trim().isEmpty()) {
            try {
                return Optional.of(LocalTime.parse(dateStr));
            } catch (DateTimeParseException ignored) {
                errors.rejectValue(field, msg);
            }
        }
        return Optional.empty();
    }
}
