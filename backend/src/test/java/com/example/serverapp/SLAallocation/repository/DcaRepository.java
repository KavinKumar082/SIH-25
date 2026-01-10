package com.example.serverapp.allocation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.serverapp.allocation.model.Dca;

public interface DcaRepository extends JpaRepository<Dca, Long> {
    List<Dca> findByStatus(Dca.Status status);
}
