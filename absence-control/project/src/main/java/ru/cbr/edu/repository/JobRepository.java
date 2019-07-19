package ru.cbr.edu.repository;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import ru.cbr.edu.model.Job;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface JobRepository extends CrudRepository<Job, Integer> {
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<Job> findById(Integer id);
}