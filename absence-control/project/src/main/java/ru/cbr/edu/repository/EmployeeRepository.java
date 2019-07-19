package ru.cbr.edu.repository;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import ru.cbr.edu.model.Employee;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<Employee> findById(Integer id);
}