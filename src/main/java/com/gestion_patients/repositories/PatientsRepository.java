package com.gestion_patients.repositories;

import com.gestion_patients.entities.Patients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientsRepository extends JpaRepository<Patients,Long> {
        Page<Patients> findByNameContains(String kw, Pageable pageable);
}
