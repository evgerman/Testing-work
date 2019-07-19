package ru.cbr.edu.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.cbr.edu.model.Employee;
import ru.cbr.edu.model.Job;

import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Transactional
public class JobRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JobRepository jobRepository;

    @Test
    public void testFindJobById() {
        Job expected = new Job("testPos");
        entityManager.persist(expected);
        entityManager.flush();

        Optional<Job> actual = jobRepository.findById(expected.getId());

        assertNotNull("Actual job should not be null", actual);
        assertThat(expected.getId()).isEqualTo(actual.get().getId());
    }
}
