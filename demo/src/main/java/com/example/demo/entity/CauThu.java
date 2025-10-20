package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class CauThu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private LocalDate birthday;
    private String exprience;
    private String location;
    @Column(columnDefinition = "LONGTEXT")
    private String image;
    @ManyToOne
    @JoinColumn(name="doituyen_id")
    private DoiTuyen doiTuyen;
}
