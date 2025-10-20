package com.example.demo.dto;

import com.example.demo.entity.DoiTuyen;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
public class CauThuDTO {
    private int id;
    private String name;
    private LocalDate birthday;
    private String exprience;
    private String location;
    private String image;
    @NotNull(message = "Chon doi tuyen")
    private DoiTuyen doiTuyen;
}
