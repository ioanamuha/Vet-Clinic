package com.finalproject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor_details")
public class  DoctorDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "specializare", nullable = false)
    private String specializare;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public DoctorDetails() {}

    public DoctorDetails(String specializare) {
        this.specializare = specializare;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "DoctorDetails{" +
                "id=" + id +
                ", specializare='" + specializare + '\'' +
                ", user=" + user +
                '}';
    }
}
