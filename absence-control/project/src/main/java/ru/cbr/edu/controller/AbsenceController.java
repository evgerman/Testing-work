package ru.cbr.edu.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.cbr.edu.model.Absence;
import ru.cbr.edu.repository.AbsenceRepository;
import ru.cbr.edu.repository.EmployeeRepository;
import ru.cbr.edu.repository.JobRepository;
import ru.cbr.edu.search.AbsenceSpecificationBuilder;
import ru.cbr.edu.search.SearchAbsence;
import ru.cbr.edu.search.SearchOperation;
import ru.cbr.edu.validator.AddAbsenceFormValidator;
import ru.cbr.edu.validator.SearchAbsenceFormValidator;

@Controller
public class AbsenceController {
    private static final Logger LOG = LogManager.getLogger(AbsenceController.class);
    private static final String FORM_PAGE = "form";

    @Autowired
    private AbsenceRepository absenceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private AddAbsenceFormValidator formValidator;
    @Autowired
    private SearchAbsenceFormValidator searchFormValidator;

    @RequestMapping("/")
    public ModelAndView initForm() {
        return getModelAndView(null, null, false);
    }

    @RequestMapping(value = "/addEmployeeAbsence")
    public String addAbsence(@Validated @ModelAttribute("newAbsence") Absence absence, BindingResult result, ModelMap model) {
        formValidator.validate(absence, result);

        if (result.hasErrors()) {
            LOG.error(result);
            model.addAllAttributes(getModelAndView(absence, null, false).getModel());
            return FORM_PAGE;
        }

        LOG.debug("Request to add {}", absence);
        absenceRepository.save(absence);
        model.addAllAttributes(getModelAndView(null, null, false).getModel());
        return FORM_PAGE;
    }

    @RequestMapping(value = "/getEmployeeAbsences")
    public String getEmployeeAbsences(@Validated @ModelAttribute("searchAbsence") SearchAbsence searchAbsence, BindingResult result , ModelMap model) {
        searchFormValidator.validate(searchAbsence, result);

        if (result.hasErrors()) {
            LOG.error(result);
            model.addAllAttributes(getModelAndView(null, searchAbsence, false).getModel());
            return FORM_PAGE;
        }

        LOG.debug("Request to search {}", searchAbsence);
        model.addAllAttributes(getModelAndView(null, searchAbsence, true).getModel());
        return FORM_PAGE;
    }

    private ModelAndView getModelAndView(Absence newAbsence, SearchAbsence searchAbsence, boolean customSearch) {
        ModelAndView mv = new ModelAndView("/form");
        mv.addObject("absenceHistory", !customSearch ? absenceRepository.findAll() : absenceRepository.findAll(getSpec(searchAbsence)));
        mv.addObject("employees", employeeRepository.findAll());
        mv.addObject("Jobs", jobRepository.findAll());
        mv.addObject("newAbsence", newAbsence == null ? new Absence() : newAbsence);
        mv.addObject("searchAbsence", searchAbsence == null ? new SearchAbsence() : searchAbsence);
        mv.addObject("dateSearchOps", SearchOperation.getDateSearchOps());
        return mv;
    }

    private Specification<Absence> getSpec(SearchAbsence searchAbsence) {
        AbsenceSpecificationBuilder builder = new AbsenceSpecificationBuilder();
        if (searchAbsence.getDate() != null) {
            builder.with("date", searchAbsence.getDateOp(), searchAbsence.getDate());
        }
        if (searchAbsence.getStartTime() != null) {
            builder.with("startTime", searchAbsence.getStartTimeOp(), searchAbsence.getStartTime());
        }
        if (searchAbsence.getEndTime() != null) {
            builder.with("endTime", searchAbsence.getEndTimeOp(), searchAbsence.getEndTime());
        }
        if (searchAbsence.getCause() != null && !searchAbsence.getCause().trim().isEmpty()) {
            builder.with("cause", SearchOperation.CONTAINS.getName(), searchAbsence.getCause());
        }
        LOG.debug(builder);
        return builder.build();
    }
}
