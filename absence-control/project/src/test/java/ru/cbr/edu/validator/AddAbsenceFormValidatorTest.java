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

import java.time.LocalDate;
import java.time.LocalTime;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class AddAbsenceFormValidatorTest {
    private Validator validator;
    private Absence absence;

    @Before
    public void setUp() {
        validator = new AddAbsenceFormValidator();
        absence = new Absence(new Employee("name"), new Job("pos"),
                LocalDate.now(), LocalTime.now(), LocalTime.now(), "cause");
    }

    @Test
    public void testSupports() {
        assertTrue(validator.supports(Absence.class));
        assertFalse(validator.supports(Object.class));
    }

    @Test
    public void testSuccessfulValidation() {
        absence.setDateStr(absence.getDate().toString());
        absence.setEndTimeStr(absence.getEndTime().toString());
        absence.setStartTimeStr(absence.getStartTime().toString());
        BindException errors = new BindException(absence, "absence");
        ValidationUtils.invokeValidator(validator, absence, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testDateMissed() {
        absence.setEndTimeStr(absence.getEndTime().toString());
        absence.setStartTimeStr(absence.getStartTime().toString());
        BindException errors = new BindException(absence, "absence");
        ValidationUtils.invokeValidator(validator, absence, errors);
        assertTrue(errors.hasErrors());
        assertEquals("form.empty.date", errors.getFieldErrors("dateStr").get(0).getCode());
    }

    @Test
    public void testStartTimeMissed() {
        absence.setDateStr(absence.getDate().toString());
        absence.setStartTimeStr(absence.getStartTime().toString());
        BindException errors = new BindException(absence, "absence");
        ValidationUtils.invokeValidator(validator, absence, errors);
        assertTrue(errors.hasErrors());
        assertEquals("form.empty.endTime", errors.getFieldErrors("endTimeStr").get(0).getCode());
    }

    @Test
    public void testEndTimeMissed() {
        absence.setDateStr(absence.getDate().toString());
        absence.setEndTimeStr(absence.getEndTime().toString());
        BindException errors = new BindException(absence, "absence");
        ValidationUtils.invokeValidator(validator, absence, errors);
        assertTrue(errors.hasErrors());
        assertEquals("form.empty.startTime", errors.getFieldErrors("startTimeStr").get(0).getCode());
    }

    @Test
    public void testInvalidDateFormat() {
        absence.setDateStr("formatted");
        absence.setEndTimeStr(absence.getEndTime().toString());
        absence.setStartTimeStr(absence.getStartTime().toString());
        BindException errors = new BindException(absence, "absence");
        ValidationUtils.invokeValidator(validator, absence, errors);
        assertTrue(errors.hasErrors());
        assertEquals("form.format.date", errors.getFieldErrors("dateStr").get(0).getCode());
    }

    @Test
    public void testInvalidStartTimeFormat() {
        absence.setDateStr(absence.getDate().toString());
        absence.setEndTimeStr(absence.getEndTime().toString());
        absence.setStartTimeStr("formatted");
        BindException errors = new BindException(absence, "absence");
        ValidationUtils.invokeValidator(validator, absence, errors);
        assertTrue(errors.hasErrors());
        assertEquals("form.format.startTime", errors.getFieldErrors("startTimeStr").get(0).getCode());
    }

    @Test
    public void testInvalidEndTimeFormat() {
        absence.setDateStr(absence.getDate().toString());
        absence.setStartTimeStr(absence.getStartTime().toString());
        absence.setEndTimeStr("formatted");
        BindException errors = new BindException(absence, "absence");
        ValidationUtils.invokeValidator(validator, absence, errors);
        assertTrue(errors.hasErrors());
        assertEquals("form.format.endTime", errors.getFieldErrors("endTimeStr").get(0).getCode());
    }
}
