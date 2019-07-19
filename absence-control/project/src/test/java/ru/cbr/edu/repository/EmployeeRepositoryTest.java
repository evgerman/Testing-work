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

import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Transactional
public class EmployeeRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindEmployeeById() {
        Employee expected = new Employee("name");
        entityManager.persist(expected);
        entityManager.flush();

        Optional<Employee> actual = employeeRepository.findById(expected.getId());

        assertNotNull("Actual employee should not be null", actual);
        assertThat(expected.getId()).isEqualTo(actual.get().getId());
    }
}
