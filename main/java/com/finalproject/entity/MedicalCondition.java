package com.finalproject.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "medicalcondition_trial")
public class MedicalCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medical_condition")
    private String medicalCondition;

    @Column(name = "medical_prescription")
    private String medicalPrescription;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @OneToOne(mappedBy = "medicalCondition")
    private Appointment appointment;

    public MedicalCondition() {}

    public MedicalCondition(String medicalCondition, String medicalPrescription, Pet pet, User doctor) {
        this.medicalCondition = medicalCondition;
        this.medicalPrescription = medicalPrescription;
        this.pet = pet;
        this.doctor = doctor;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public String getMedicalPrescription() {
        return medicalPrescription;
    }

    public void setMedicalPrescription(String medicalPrescription) {
        this.medicalPrescription = medicalPrescription;
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
        return "MedicalCondition{" +
                "id=" + id +
                ", medicalCondition='" + medicalCondition + '\'' +
                ", medicalPrescription='" + medicalPrescription + '\'' +
                ", pet=" + pet +
                ", doctor=" + doctor +
                '}';
    }
}
