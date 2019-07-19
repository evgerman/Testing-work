package ru.cbr.edu.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.cbr.edu.model.Absence;

public interface AbsenceRepository extends CrudRepository<Absence, Integer>, JpaSpecificationExecutor<Absence> {

}