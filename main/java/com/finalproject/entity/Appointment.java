package com.finalproject.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "appointment_trial")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`interval`", nullable = false)
    private Integer interval;

    @Column(name = "day")
    private LocalDate day;

    @OneToOne(mappedBy = "appointment")
    private MedicalCondition medicalCondition;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public Appointment() {}

    public Appointment(Integer interval, LocalDate day, MedicalCondition medicalCondition) {
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

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public MedicalCondition getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(MedicalCondition medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
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
