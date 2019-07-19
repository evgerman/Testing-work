package ru.cbr.edu.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import ru.cbr.edu.WebInitializer;
import ru.cbr.edu.model.Absence;
import ru.cbr.edu.model.Employee;
import ru.cbr.edu.model.Job;
import ru.cbr.edu.repository.AbsenceRepository;
import ru.cbr.edu.repository.EmployeeRepository;
import ru.cbr.edu.repository.JobRepository;
import ru.cbr.edu.validator.AddAbsenceFormValidator;
import ru.cbr.edu.validator.SearchAbsenceFormValidator;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WebInitializer.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase
@Transactional
public class AbsenceControllerIntTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private AbsenceRepository absenceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    AddAbsenceFormValidator formValidator;
    @Autowired
    SearchAbsenceFormValidator searchFormValidator;

    private Absence firstAbsence;
    private Absence secondAbsence;

    @Before
    public  void setUp() {
        Employee employee = new Employee("testName");
        employeeRepository.save(employee);
        Job job = new Job("testPosition");
        jobRepository.save(job);
        firstAbsence = new Absence(employee, job, LocalDate.now(), LocalTime.now(), LocalTime.now(), "testCause");
        secondAbsence = new Absence(employee, job, LocalDate.now(), LocalTime.now(), LocalTime.now(), "testCause2");
        absenceRepository.save(firstAbsence);
        absenceRepository.save(secondAbsence);
    }

    @Test
    public void testGetIndexPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/form"))
                .andExpect(forwardedUrl("/WEB-INF/jsp//form.jsp"))
                .andExpect(model().attribute("absenceHistory", hasSize(2)))
                .andExpect(getAbsenceResultMatcher(firstAbsence))
                .andExpect(getAbsenceResultMatcher(secondAbsence));
    }

    @Test
    public void testSearchPage() throws Exception {
        mvc.perform(post("/getEmployeeAbsences?cause=" + secondAbsence.getCause()))
                .andExpect(status().isOk())
                .andExpect(view().name("form"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"))
                .andExpect(model().attribute("absenceHistory", hasSize(1)))
                .andExpect(getAbsenceResultMatcher(secondAbsence));
    }

    @Test
    public void testAddPage() throws Exception {
        Absence newAbsence = new Absence(firstAbsence.getEmployee(), firstAbsence.getJob(), LocalDate.now(),
                LocalTime.now(), LocalTime.now(), "testNewCause");
        newAbsence.setId(3);
        String params = String.format("employee=%d&job=%d&dateStr=%s&startTimeStr=%s&endTimeStr=%s&cause=%s",
                newAbsence.getEmployee().getId(), newAbsence.getJob().getId(), newAbsence.getDate().toString(),
                newAbsence.getStartTime().toString(), newAbsence.getEndTime().toString(), newAbsence.getCause());
        mvc.perform(post("/addEmployeeAbsence?" + params))
                .andExpect(status().isOk())
                .andExpect(view().name("form"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"))
                .andExpect(model().attribute("absenceHistory", hasSize(3)))
                .andExpect(getAbsenceResultMatcher(firstAbsence))
                .andExpect(getAbsenceResultMatcher(secondAbsence))
                .andExpect(getAbsenceResultMatcher(newAbsence));
    }

    private ResultMatcher getAbsenceResultMatcher(Absence absence) {
        return model().attribute("absenceHistory", hasItem(
                allOf(
                        hasProperty("id", is(absence.getId())),
                        hasProperty("employee", allOf(
                                hasProperty("id", is(absence.getEmployee().getId())),
                                hasProperty("name", is(absence.getEmployee().getName()))
                        )),
                        hasProperty("job", allOf(
                                hasProperty("id", is(absence.getJob().getId())),
                                hasProperty("position", is(absence.getJob().getPosition()))
                        )),
                        hasProperty("date", is(absence.getDate())),
                        hasProperty("startTime", is(absence.getStartTime())),
                        hasProperty("endTime", is(absence.getEndTime())),
                        hasProperty("cause", is(absence.getCause()))
                )
        ));
    }
}