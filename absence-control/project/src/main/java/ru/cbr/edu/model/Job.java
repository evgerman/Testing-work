package ru.cbr.edu.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name = "job_seq", sequenceName = "job_seq", allocationSize = 1)
public class Job {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_seq")
    @Id
    private Integer id;

    @Column
    private String position;

    public Job() {
        // hibernate required
    }

    public Job(String position) {
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        return id.equals(job.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", position='" + position + '\'' +
                '}';
    }
}