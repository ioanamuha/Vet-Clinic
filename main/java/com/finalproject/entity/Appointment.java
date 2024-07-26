package com.finalproject.entity;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "appointment_trial")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interval", nullable = false)
    private Integer interval;

    @Column(name = "day")
    private Date day;

    @OneToOne
    @JoinColumn(name = "medicalcondition_id", nullable = false)
    private MedicalCondition medicalCondition;

    public Appointment() {}

    public Appointment(Integer interval, Date day, MedicalCondition medicalCondition) {
        this.interval = interval;
        this.day = day;
        this.medicalCondition = medicalCondition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public MedicalCondition getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(MedicalCondition medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", interval=" + interval +
                ", day=" + day +
                ", medicalCondition=" + medicalCondition +
                '}';
    }
}
