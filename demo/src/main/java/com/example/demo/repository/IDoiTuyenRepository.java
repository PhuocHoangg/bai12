package com.example.demo.repository;

import com.example.demo.entity.DoiTuyen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDoiTuyenRepository extends JpaRepository<DoiTuyen, Integer> {
    List<DoiTuyen> findAll();
}
