package com.edutech.team26.repository;

import com.edutech.team26.domain.Files;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilesRepository extends JpaRepository<Files, Long> {

    List<Files> findByPar(Long par);

    List<Files> findByParAndToUse(Long par, String toUse);

}