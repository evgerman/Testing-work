package ru.cbr.edu.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.cbr.edu.model.Absence;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Component
public class AddAbsenceFormValidator extends BaseValidator {
    private static final Logger LOG = LogManager.getLogger(AddAbsenceFormValidator.class);

    @Override
    public boolean supports(Class<?> clazz) {
        return Absence.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Absence absence = (Absence) target;
        LOG.debug("Validation absence {}", absence);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employee", "form.empty.employee");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "job", "form.empty.job");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateStr", "form.empty.date");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startTimeStr", "form.empty.startTime");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endTimeStr", "form.empty.endTime");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cause", "form.empty.cause");

        checkAndParseDate(absence, errors, "dateStr", "form.format.date");
        checkAndParseTime(absence, errors, "startTimeStr", "form.format.startTime", true);
        checkAndParseTime(absence, errors, "endTimeStr", "form.format.endTime", false);
    }
}