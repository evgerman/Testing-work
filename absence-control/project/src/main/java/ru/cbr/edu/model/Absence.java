package ru.cbr.edu.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "absence_history")
@SequenceGenerator(name = "absence_seq", sequenceName = "absence_seq", allocationSize = 1)
public class Absence {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "absence_seq")
    @Id
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_id")
    private Job job;

    @Column
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Column(name = "start_time")
    @Convert(converter = Jsr310JpaConverters.LocalTimeConverter.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;

    @Column(name = "end_time")
    @Convert(converter = Jsr310JpaConverters.LocalTimeConverter.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;

    @Column
    private String cause;

    @Transient
    private String dateStr;
    @Transient
    private String startTimeStr;
    @Transient
    private String endTimeStr;

    public Absence() {
        // hibernate required
    }

    public Absence(Employee employee, Job job, LocalDate date, LocalTime startTime, LocalTime endTime, String cause) {
        this.employee = employee;
        this.job = job;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cause = cause;
    }

    public Integer getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Job getJob() {
        return job;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getCause() {
        return cause;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Absence absence = (Absence) o;

        return id.equals(absence.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Absence{" +
                "id=" + id +
                ", employee=" + employee +
                ", job=" + job +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", cause='" + cause + '\'' +
                '}';
    }
}