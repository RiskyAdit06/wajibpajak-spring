package com.example.wajibpajak.repository;

import com.example.wajibpajak.model.WajibPajakModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WajibPajakRepository extends JpaRepository<WajibPajakModel, Long> {
    Optional<WajibPajakModel> findByNpwpd(String npwpd);
}