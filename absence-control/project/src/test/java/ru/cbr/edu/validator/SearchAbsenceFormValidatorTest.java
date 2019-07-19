package ru.cbr.edu.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.cbr.edu.model.Absence;
import ru.cbr.edu.model.Employee;
import ru.cbr.edu.model.Job;
import ru.cbr.edu.search.SearchAbsence;

import java.time.LocalDate;
import java.time.LocalTime;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class SearchAbsenceFormValidatorTest {
    private Validator validator;
    private SearchAbsence absence;

    @Before
    public void setUp() {
        validator = new SearchAbsenceFormValidator();
        absence = new SearchAbsence();
    }

    @Test
    public void testSupports() {
        assertTrue(validator.supports(SearchAbsence.class));
        assertFalse(validator.supports(Object.class));
    }

    @Test
    public void testSuccessfulValidation() {
        BindException errors = new BindException(absence, "absence");
        ValidationUtils.invokeValidator(validator, absence, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testInvalidDateFormat() {
        absence.setDateStr("formatted");
        BindException errors = new BindException(absence, "absence");
        ValidationUtils.invokeValidator(validator, absence, errors);
        assertTrue(errors.hasErrors());
        assertEquals("search.format.date", errors.getFieldErrors("date").get(0).getCode());
    }

    @Test
    public void testInvalidStartTimeFormat() {
        absence.setStartTimeStr("formatted");
        BindException errors = new BindException(absence, "absence");
        ValidationUtils.invokeValidator(validator, absence, errors);
        assertTrue(errors.hasErrors());
        assertEquals("search.format.startTime", errors.getFieldErrors("startTime").get(0).getCode());
    }

    @Test
    public void testInvalidEndTimeFormat() {
        absence.setEndTimeStr("formatted");
        BindException errors = new BindException(absence, "absence");
        ValidationUtils.invokeValidator(validator, absence, errors);
        assertTrue(errors.hasErrors());
        assertEquals("search.format.endTime", errors.getFieldErrors("endTime").get(0).getCode());
    }
}
