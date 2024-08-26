package com.finalproject.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "medical_file")
public class MedicalFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medical_condition")
    private String medicalCondition;

    @Column(name = "medical_prescription")
    private String medicalPrescription;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    public MedicalFile() {}

    public MedicalFile(String medicalCondition, String medicalPrescription) {
        this.medicalCondition = medicalCondition;
        this.medicalPrescription = medicalPrescription;
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

    @Override
    public String toString() {
        return "MedicalCondition{" +
                "id=" + id +
                ", medicalCondition='" + medicalCondition + '\'' +
                ", medicalPrescription='" + medicalPrescription + '\'' +
                '}';
    }
}
