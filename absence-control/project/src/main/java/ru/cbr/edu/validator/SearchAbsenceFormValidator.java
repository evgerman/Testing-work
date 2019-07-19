package ru.cbr.edu.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.cbr.edu.search.SearchAbsence;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Component
public class SearchAbsenceFormValidator extends BaseValidator {
    private static final Logger LOG = LogManager.getLogger(SearchAbsenceFormValidator.class);

    @Override
    public boolean supports(Class<?> clazz) {
        return SearchAbsence.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SearchAbsence searchAbsence = (SearchAbsence) target;
        LOG.trace("Errors {}", errors);
        LOG.debug("Validation search absence {}", searchAbsence);

        checkAndParseDate(searchAbsence, errors, "date", "search.format.date");
        checkAndParseTime(searchAbsence, errors, "startTime", "search.format.startTime", true);
        checkAndParseTime(searchAbsence, errors, "endTime", "search.format.endTime", false);
    }
}